package com.palazzisoft.skyquest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palazzisoft.skyquest.model.Messier;
import com.palazzisoft.skyquest.model.MessierObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@AllArgsConstructor
@Service
@Slf4j
public class CatalogService {


    public Map<String, MessierObject> getMessierCatalog() {
        log.info("Requesting catalog for Messier objects");

        Map<String, MessierObject> result;
        try {
            Messier messier = readFile();
            result = messier.data();
        } catch (IOException e) {
            log.error("Error retrieving catalog for Messier objects");
            throw new RuntimeException(e);
        }

        return result;
    }

    private Messier readFile() throws IOException {
        log.info("Requesting catalog for Messier objects, that's reality");
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/messier.json");
        return mapper.readValue(inputStream, Messier.class);
    }
}

package com.palazzisoft.skyquest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palazzisoft.skyquest.model.Messier;
import com.palazzisoft.skyquest.model.MessierObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class CatalogService {

    public Map<String, MessierObject> getMessierCatalog() {
        log.info("Requesting catalog for Messier objects");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, MessierObject> result;
        try {
            Messier messier = mapper.readValue(new ClassPathResource("/data/messier.json").getFile(), Messier.class);
            result = messier.data();
        } catch (IOException e) {
            log.error("Error retrieving catalog for Messier objects");
            throw new RuntimeException(e);
        }

        return result;
    }
}

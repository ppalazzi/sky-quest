package com.palazzisoft.skyquest.controller;

import com.palazzisoft.skyquest.model.MessierObject;
import com.palazzisoft.skyquest.service.CatalogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/catalog")
@Slf4j
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping (value = "/messier")
    public ResponseEntity<Map<String, MessierObject>> getMessierCatalog() {
        log.info("Requesting catalog for Messier objects");
        return ResponseEntity.ok(catalogService.getMessierCatalog());
    }

}

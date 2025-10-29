package com.palazzisoft.skyquest.controller;

import com.palazzisoft.skyquest.entity.Eyepiece;
import com.palazzisoft.skyquest.service.EyepieceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/eyepieces")
@Slf4j
public class EyepieceController {

    private final EyepieceService eyepieceService;

    @GetMapping
    public ResponseEntity<List<Eyepiece>> getAllEyepieces() {
        log.info("Getting all eyepieces");
        List<Eyepiece> eyepieces = eyepieceService.findAll();
        return ResponseEntity.ok(eyepieces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eyepiece> getEyepieceById(@PathVariable Long id) {
        log.info("Getting eyepiece by id: {}", id);
        Optional<Eyepiece> eyepiece = eyepieceService.findById(id);
        return eyepiece.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Eyepiece>> getEyepiecesByBrand(@PathVariable String brand) {
        log.info("Getting eyepieces by brand: {}", brand);
        List<Eyepiece> eyepieces = eyepieceService.findByBrand(brand);
        return ResponseEntity.ok(eyepieces);
    }

    @GetMapping("/focal-length/{focalLength}")
    public ResponseEntity<List<Eyepiece>> getEyepiecesByFocalLength(@PathVariable String focalLength) {
        log.info("Getting eyepieces by focal length: {}", focalLength);
        List<Eyepiece> eyepieces = eyepieceService.findByFocalLength(focalLength);
        return ResponseEntity.ok(eyepieces);
    }

    @GetMapping("/barrel-size/{barrelSize}")
    public ResponseEntity<List<Eyepiece>> getEyepiecesByBarrelSize(@PathVariable String barrelSize) {
        log.info("Getting eyepieces by barrel size: {}", barrelSize);
        List<Eyepiece> eyepieces = eyepieceService.findByBarrelSize(barrelSize);
        return ResponseEntity.ok(eyepieces);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Eyepiece>> searchEyepieces(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String focalLength,
            @RequestParam(required = false) String barrelSize) {
        log.info("Searching eyepieces with brand: {}, model: {}, focalLength: {}, barrelSize: {}",
                brand, model, focalLength, barrelSize);

        if (focalLength != null) {
            return ResponseEntity.ok(eyepieceService.findByFocalLength(focalLength));
        } else if (barrelSize != null) {
            return ResponseEntity.ok(eyepieceService.findByBarrelSize(barrelSize));
        } else if (brand != null) {
            return ResponseEntity.ok(eyepieceService.searchByBrand(brand));
        } else if (model != null) {
            return ResponseEntity.ok(eyepieceService.searchByModel(model));
        } else {
            return ResponseEntity.ok(eyepieceService.findAll());
        }
    }

    @PostMapping
    public ResponseEntity<Eyepiece> createEyepiece(@RequestBody Eyepiece eyepiece) {
        log.info("Creating eyepiece: {} {}", eyepiece.getBrand(), eyepiece.getModel());
        try {
            Eyepiece savedEyepiece = eyepieceService.save(eyepiece);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEyepiece);
        } catch (IllegalArgumentException e) {
            log.error("Error creating eyepiece: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Eyepiece> updateEyepiece(@PathVariable Long id, @RequestBody Eyepiece eyepiece) {
        log.info("Updating eyepiece with id: {}", id);
        if (!eyepieceService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        eyepiece.setId(id);
        try {
            Eyepiece updatedEyepiece = eyepieceService.save(eyepiece);
            return ResponseEntity.ok(updatedEyepiece);
        } catch (IllegalArgumentException e) {
            log.error("Error updating eyepiece: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEyepiece(@PathVariable Long id) {
        log.info("Deleting eyepiece with id: {}", id);
        try {
            eyepieceService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Error deleting eyepiece: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
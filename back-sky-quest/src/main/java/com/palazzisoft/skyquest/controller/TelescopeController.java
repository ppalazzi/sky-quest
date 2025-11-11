package com.palazzisoft.skyquest.controller;

import com.palazzisoft.skyquest.entity.Telescope;
import com.palazzisoft.skyquest.entity.TelescopeType;
import com.palazzisoft.skyquest.service.TelescopeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/telescopes")
@Slf4j
public class TelescopeController {

    private final TelescopeService telescopeService;

    @GetMapping
    public ResponseEntity<List<Telescope>> getAllTelescopes() {
        log.info("Getting all telescopes");
        List<Telescope> telescopes = telescopeService.findAll();
        return ResponseEntity.ok(telescopes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Telescope> getTelescopeById(@PathVariable Long id) {
        log.info("Getting telescope by id: {}", id);
        Optional<Telescope> telescope = telescopeService.findById(id);
        return telescope.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Telescope>> getTelescopesByBrand(@PathVariable String brand) {
        log.info("Getting telescopes by brand: {}", brand);
        List<Telescope> telescopes = telescopeService.findByBrand(brand);
        return ResponseEntity.ok(telescopes);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Telescope>> getTelescopesByType(@PathVariable TelescopeType type) {
        log.info("Getting telescopes by type: {}", type);
        List<Telescope> telescopes = telescopeService.findByType(type);
        return ResponseEntity.ok(telescopes);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Telescope>> searchTelescopes(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) TelescopeType type) {
        log.info("Searching telescopes with brand: {}, model: {}, type: {}", brand, model, type);

        if (brand != null && type != null) {
            return ResponseEntity.ok(telescopeService.findByBrandAndType(brand, type));
        } else if (brand != null) {
            return ResponseEntity.ok(telescopeService.searchByBrand(brand));
        } else if (model != null) {
            return ResponseEntity.ok(telescopeService.searchByModel(model));
        } else if (type != null) {
            return ResponseEntity.ok(telescopeService.findByType(type));
        } else {
            return ResponseEntity.ok(telescopeService.findAll());
        }
    }

    @PostMapping
    public ResponseEntity<Telescope> createTelescope(@RequestBody Telescope telescope) {
        log.info("Creating telescope: {} {}", telescope.getBrand(), telescope.getModel());

        Telescope savedTelescope = telescopeService.save(telescope);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTelescope);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Telescope> updateTelescope(@PathVariable Long id, @RequestBody Telescope telescope) {
        log.info("Updating telescope with id: {}", id);
        if (!telescopeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        telescope.setId(id);
        try {
            Telescope updatedTelescope = telescopeService.save(telescope);
            return ResponseEntity.ok(updatedTelescope);
        } catch (IllegalArgumentException e) {
            log.error("Error updating telescope: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelescope(@PathVariable Long id) {
        log.info("Deleting telescope with id: {}", id);
        try {
            telescopeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Error deleting telescope: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
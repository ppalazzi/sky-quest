package com.palazzisoft.skyquest.controller;

import com.palazzisoft.skyquest.entity.Camera;
import com.palazzisoft.skyquest.service.CameraService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cameras")
@Slf4j
public class CameraController {

    private final CameraService cameraService;

    @GetMapping
    public ResponseEntity<List<Camera>> getAllCameras() {
        log.info("Getting all cameras");
        List<Camera> cameras = cameraService.findAll();
        return ResponseEntity.ok(cameras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Camera> getCameraById(@PathVariable Long id) {
        log.info("Getting camera by id: {}", id);
        Optional<Camera> camera = cameraService.findById(id);
        return camera.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Camera>> getCamerasByBrand(@PathVariable String brand) {
        log.info("Getting cameras by brand: {}", brand);
        List<Camera> cameras = cameraService.findByBrand(brand);
        return ResponseEntity.ok(cameras);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Camera>> searchCameras(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model) {
        log.info("Searching cameras with brand: {}, model: {}",
                brand, model);

        if (brand != null) {
            ResponseEntity.ok(cameraService.searchByBrand(brand));
        } else if (model != null) {
            return ResponseEntity.ok(cameraService.searchByModel(model));
        } else {
            return ResponseEntity.ok(cameraService.findAll());
        }
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    public ResponseEntity<Camera> createCamera(@RequestBody Camera camera) {
        log.info("Creating camera: {} {}", camera.getBrand(), camera.getModel());
        try {
            Camera savedCamera = cameraService.save(camera);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCamera);
        } catch (IllegalArgumentException e) {
            log.error("Error creating camera: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Camera> updateCamera(@PathVariable Long id, @RequestBody Camera camera) {
        log.info("Updating camera with id: {}", id);
        if (!cameraService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        camera.setId(id);
        try {
            Camera updatedCamera = cameraService.save(camera);
            return ResponseEntity.ok(updatedCamera);
        } catch (IllegalArgumentException e) {
            log.error("Error updating camera: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCamera(@PathVariable Long id) {
        log.info("Deleting camera with id: {}", id);
        try {
            cameraService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Error deleting camera: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
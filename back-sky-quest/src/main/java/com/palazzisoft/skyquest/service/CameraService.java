package com.palazzisoft.skyquest.service;

import com.palazzisoft.skyquest.entity.Camera;
import com.palazzisoft.skyquest.repository.CameraRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@AllArgsConstructor
public class CameraService {

    private final CameraRepository cameraRepository;

    public List<Camera> findAll() {
        log.debug("Finding all cameras");
        return StreamSupport.stream(cameraRepository.findAll().spliterator(), false)
                .toList();
    }

    public Optional<Camera> findById(Long id) {
        log.debug("Finding camera by id: {}", id);
        return cameraRepository.findById(id);
    }

    public List<Camera> findByBrand(String brand) {
        log.debug("Finding cameras by brand: {}", brand);
        return cameraRepository.findByBrand(brand);
    }

    public List<Camera> searchByBrand(String brand) {
        log.debug("Searching cameras by brand containing: {}", brand);
        return cameraRepository.findByBrandContainingIgnoreCase(brand);
    }

    public List<Camera> searchByModel(String model) {
        log.debug("Searching cameras by model containing: {}", model);
        return cameraRepository.findByModelContainingIgnoreCase(model);
    }

    public Camera save(Camera camera) {
        log.debug("Saving camera: {} {}", camera.getBrand(), camera.getModel());

        // Validate unique brand and model combination
        Optional<Camera> existing = cameraRepository.findByBrandAndModel(
                camera.getBrand(), camera.getModel());
        if (existing.isPresent() && !existing.get().getId().equals(camera.getId())) {
            throw new IllegalArgumentException("Camera with this brand and model already exists");
        }

        return cameraRepository.save(camera);
    }

    public void deleteById(Long id) {
        log.debug("Deleting camera by id: {}", id);
        if (!cameraRepository.existsById(id)) {
            throw new IllegalArgumentException("Camera with id " + id + " does not exist");
        }
        cameraRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return cameraRepository.existsById(id);
    }
}
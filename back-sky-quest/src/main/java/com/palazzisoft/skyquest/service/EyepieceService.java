package com.palazzisoft.skyquest.service;

import com.palazzisoft.skyquest.entity.Eyepiece;
import com.palazzisoft.skyquest.repository.EyepieceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@AllArgsConstructor
public class EyepieceService {

    private final EyepieceRepository eyepieceRepository;

    public List<Eyepiece> findAll() {
        log.debug("Finding all eyepieces");
        return StreamSupport.stream(eyepieceRepository.findAll().spliterator(), false)
                .toList();
    }

    public Optional<Eyepiece> findById(Long id) {
        log.debug("Finding eyepiece by id: {}", id);
        return eyepieceRepository.findById(id);
    }

    public List<Eyepiece> findByBrand(String brand) {
        log.debug("Finding eyepieces by brand: {}", brand);
        return eyepieceRepository.findByBrand(brand);
    }

    public List<Eyepiece> findByFocalLength(String focalLength) {
        log.debug("Finding eyepieces by focal length: {}", focalLength);
        return eyepieceRepository.findByFocalLength(focalLength);
    }

    public List<Eyepiece> findByBarrelSize(String barrelSize) {
        log.debug("Finding eyepieces by barrel size: {}", barrelSize);
        return eyepieceRepository.findByBarrelSize(barrelSize);
    }

    public Optional<Eyepiece> findByBrandAndModel(String brand, String model) {
        log.debug("Finding eyepiece by brand: {} and model: {}", brand, model);
        return eyepieceRepository.findByBrandAndModel(brand, model);
    }

    public List<Eyepiece> searchByBrand(String brand) {
        log.debug("Searching eyepieces by brand containing: {}", brand);
        return eyepieceRepository.findByBrandContainingIgnoreCase(brand);
    }

    public List<Eyepiece> searchByModel(String model) {
        log.debug("Searching eyepieces by model containing: {}", model);
        return eyepieceRepository.findByModelContainingIgnoreCase(model);
    }

    public Eyepiece save(Eyepiece eyepiece) {
        log.debug("Saving eyepiece: {} {}", eyepiece.getBrand(), eyepiece.getModel());

        // Validate unique brand and model combination
        Optional<Eyepiece> existing = eyepieceRepository.findByBrandAndModel(
                eyepiece.getBrand(), eyepiece.getModel());
        if (existing.isPresent() && !existing.get().getId().equals(eyepiece.getId())) {
            throw new IllegalArgumentException("Eyepiece with this brand and model already exists");
        }

        return eyepieceRepository.save(eyepiece);
    }

    public void deleteById(Long id) {
        log.debug("Deleting eyepiece by id: {}", id);
        if (!eyepieceRepository.existsById(id)) {
            throw new IllegalArgumentException("Eyepiece with id " + id + " does not exist");
        }
        eyepieceRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return eyepieceRepository.existsById(id);
    }
}
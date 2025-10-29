package com.palazzisoft.skyquest.service;

import com.palazzisoft.skyquest.entity.Telescope;
import com.palazzisoft.skyquest.entity.TelescopeType;
import com.palazzisoft.skyquest.repository.TelescopeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@AllArgsConstructor
public class TelescopeService {

    private final TelescopeRepository telescopeRepository;

    public List<Telescope> findAll() {
        log.debug("Finding all telescopes");
        return StreamSupport.stream(telescopeRepository.findAll().spliterator(), false)
                .toList();
    }

    public Optional<Telescope> findById(Long id) {
        log.debug("Finding telescope by id: {}", id);
        return telescopeRepository.findById(id);
    }

    public List<Telescope> findByBrand(String brand) {
        log.debug("Finding telescopes by brand: {}", brand);
        return telescopeRepository.findByBrand(brand);
    }

    public List<Telescope> findByType(TelescopeType type) {
        log.debug("Finding telescopes by type: {}", type);
        return telescopeRepository.findByType(type);
    }

    public List<Telescope> findByBrandAndType(String brand, TelescopeType type) {
        log.debug("Finding telescopes by brand: {} and type: {}", brand, type);
        return telescopeRepository.findByBrandAndType(brand, type);
    }

    public Optional<Telescope> findByBrandAndModel(String brand, String model) {
        log.debug("Finding telescope by brand: {} and model: {}", brand, model);
        return telescopeRepository.findByBrandAndModel(brand, model);
    }

    public List<Telescope> searchByBrand(String brand) {
        log.debug("Searching telescopes by brand containing: {}", brand);
        return telescopeRepository.findByBrandContainingIgnoreCase(brand);
    }

    public List<Telescope> searchByModel(String model) {
        log.debug("Searching telescopes by model containing: {}", model);
        return telescopeRepository.findByModelContainingIgnoreCase(model);
    }

    public Telescope save(Telescope telescope) {
        log.debug("Saving telescope: {} {}", telescope.getBrand(), telescope.getModel());

        // Validate unique brand and model combination
        Optional<Telescope> existing = telescopeRepository.findByBrandAndModel(
                telescope.getBrand(), telescope.getModel());
        if (existing.isPresent() && !existing.get().getId().equals(telescope.getId())) {
            throw new IllegalArgumentException("Telescope with this brand and model already exists");
        }

        return telescopeRepository.save(telescope);
    }

    public void deleteById(Long id) {
        log.debug("Deleting telescope by id: {}", id);
        if (!telescopeRepository.existsById(id)) {
            throw new IllegalArgumentException("Telescope with id " + id + " does not exist");
        }
        telescopeRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return telescopeRepository.existsById(id);
    }
}
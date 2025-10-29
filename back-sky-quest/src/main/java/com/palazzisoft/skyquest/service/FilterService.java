package com.palazzisoft.skyquest.service;

import com.palazzisoft.skyquest.entity.Filter;
import com.palazzisoft.skyquest.repository.FilterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@AllArgsConstructor
public class FilterService {

    private final FilterRepository filterRepository;

    public List<Filter> findAll() {
        log.debug("Finding all filters");
        return StreamSupport.stream(filterRepository.findAll().spliterator(), false)
                .toList();
    }

    public Optional<Filter> findById(Long id) {
        log.debug("Finding filter by id: {}", id);
        return filterRepository.findById(id);
    }

    public List<Filter> findByBrand(String brand) {
        log.debug("Finding filters by brand: {}", brand);
        return filterRepository.findByBrand(brand);
    }

    public List<Filter> findByFilterType(String filterType) {
        log.debug("Finding filters by filter type: {}", filterType);
        return filterRepository.findByFilterType(filterType);
    }

    public List<Filter> findByApplication(String application) {
        log.debug("Finding filters by application: {}", application);
        return filterRepository.findByApplication(application);
    }

    public List<Filter> findByThreadSize(String threadSize) {
        log.debug("Finding filters by thread size: {}", threadSize);
        return filterRepository.findByThreadSize(threadSize);
    }

    public Optional<Filter> findByBrandAndModel(String brand, String model) {
        log.debug("Finding filter by brand: {} and model: {}", brand, model);
        return filterRepository.findByBrandAndModel(brand, model);
    }

    public List<Filter> searchByBrand(String brand) {
        log.debug("Searching filters by brand containing: {}", brand);
        return filterRepository.findByBrandContainingIgnoreCase(brand);
    }

    public List<Filter> searchByModel(String model) {
        log.debug("Searching filters by model containing: {}", model);
        return filterRepository.findByModelContainingIgnoreCase(model);
    }

    public Filter save(Filter filter) {
        log.debug("Saving filter: {} {}", filter.getBrand(), filter.getModel());

        // Validate unique brand and model combination
        Optional<Filter> existing = filterRepository.findByBrandAndModel(
                filter.getBrand(), filter.getModel());
        if (existing.isPresent() && !existing.get().getId().equals(filter.getId())) {
            throw new IllegalArgumentException("Filter with this brand and model already exists");
        }

        return filterRepository.save(filter);
    }

    public void deleteById(Long id) {
        log.debug("Deleting filter by id: {}", id);
        if (!filterRepository.existsById(id)) {
            throw new IllegalArgumentException("Filter with id " + id + " does not exist");
        }
        filterRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return filterRepository.existsById(id);
    }
}
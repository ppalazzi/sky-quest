package com.palazzisoft.skyquest.controller;

import com.palazzisoft.skyquest.entity.Filter;
import com.palazzisoft.skyquest.service.FilterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/filters")
@Slf4j
public class FilterController {

    private final FilterService filterService;

    @GetMapping
    public ResponseEntity<List<Filter>> getAllFilters() {
        log.info("Getting all filters");
        List<Filter> filters = filterService.findAll();
        return ResponseEntity.ok(filters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filter> getFilterById(@PathVariable Long id) {
        log.info("Getting filter by id: {}", id);
        Optional<Filter> filter = filterService.findById(id);
        return filter.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Filter>> getFiltersByBrand(@PathVariable String brand) {
        log.info("Getting filters by brand: {}", brand);
        List<Filter> filters = filterService.findByBrand(brand);
        return ResponseEntity.ok(filters);
    }

    @GetMapping("/filter-type/{filterType}")
    public ResponseEntity<List<Filter>> getFiltersByFilterType(@PathVariable String filterType) {
        log.info("Getting filters by filter type: {}", filterType);
        List<Filter> filters = filterService.findByFilterType(filterType);
        return ResponseEntity.ok(filters);
    }

    @GetMapping("/application/{application}")
    public ResponseEntity<List<Filter>> getFiltersByApplication(@PathVariable String application) {
        log.info("Getting filters by application: {}", application);
        List<Filter> filters = filterService.findByApplication(application);
        return ResponseEntity.ok(filters);
    }

    @GetMapping("/thread-size/{threadSize}")
    public ResponseEntity<List<Filter>> getFiltersByThreadSize(@PathVariable String threadSize) {
        log.info("Getting filters by thread size: {}", threadSize);
        List<Filter> filters = filterService.findByThreadSize(threadSize);
        return ResponseEntity.ok(filters);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Filter>> searchFilters(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String filterType,
            @RequestParam(required = false) String application,
            @RequestParam(required = false) String threadSize) {
        log.info("Searching filters with brand: {}, model: {}, filterType: {}, application: {}, threadSize: {}",
                brand, model, filterType, application, threadSize);

        if (filterType != null) {
            return ResponseEntity.ok(filterService.findByFilterType(filterType));
        } else if (application != null) {
            return ResponseEntity.ok(filterService.findByApplication(application));
        } else if (threadSize != null) {
            return ResponseEntity.ok(filterService.findByThreadSize(threadSize));
        } else if (brand != null) {
            return ResponseEntity.ok(filterService.searchByBrand(brand));
        } else if (model != null) {
            return ResponseEntity.ok(filterService.searchByModel(model));
        } else {
            return ResponseEntity.ok(filterService.findAll());
        }
    }

    @PostMapping
    public ResponseEntity<Filter> createFilter(@RequestBody Filter filter) {
        log.info("Creating filter: {} {}", filter.getBrand(), filter.getModel());
        try {
            Filter savedFilter = filterService.save(filter);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFilter);
        } catch (IllegalArgumentException e) {
            log.error("Error creating filter: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filter> updateFilter(@PathVariable Long id, @RequestBody Filter filter) {
        log.info("Updating filter with id: {}", id);
        if (!filterService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        filter.setId(id);
        try {
            Filter updatedFilter = filterService.save(filter);
            return ResponseEntity.ok(updatedFilter);
        } catch (IllegalArgumentException e) {
            log.error("Error updating filter: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilter(@PathVariable Long id) {
        log.info("Deleting filter with id: {}", id);
        try {
            filterService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Error deleting filter: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
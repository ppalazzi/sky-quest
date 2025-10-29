package com.palazzisoft.skyquest.repository;

import com.palazzisoft.skyquest.entity.Filter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilterRepository extends CrudRepository<Filter, Long> {

    List<Filter> findByBrand(String brand);

    Optional<Filter> findByBrandAndModel(String brand, String model);

    List<Filter> findByBrandContainingIgnoreCase(String brand);

    List<Filter> findByModelContainingIgnoreCase(String model);

    List<Filter> findByFilterType(String filterType);

    List<Filter> findByApplication(String application);

    List<Filter> findByThreadSize(String threadSize);
}
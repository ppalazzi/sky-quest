package com.palazzisoft.skyquest.repository;

import com.palazzisoft.skyquest.entity.Telescope;
import com.palazzisoft.skyquest.entity.TelescopeType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelescopeRepository extends CrudRepository<Telescope, Long> {

    List<Telescope> findByBrand(String brand);

    List<Telescope> findByType(TelescopeType type);

    List<Telescope> findByBrandAndType(String brand, TelescopeType type);

    Optional<Telescope> findByBrandAndModel(String brand, String model);

    List<Telescope> findByBrandContainingIgnoreCase(String brand);

    List<Telescope> findByModelContainingIgnoreCase(String model);
}
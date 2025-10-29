package com.palazzisoft.skyquest.repository;

import com.palazzisoft.skyquest.entity.Camera;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CameraRepository extends CrudRepository<Camera, Long> {

    List<Camera> findByBrand(String brand);

    Optional<Camera> findByBrandAndModel(String brand, String model);

    List<Camera> findByBrandContainingIgnoreCase(String brand);

    List<Camera> findByModelContainingIgnoreCase(String model);
}
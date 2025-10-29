package com.palazzisoft.skyquest.repository;

import com.palazzisoft.skyquest.entity.Eyepiece;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EyepieceRepository extends CrudRepository<Eyepiece, Long> {

    List<Eyepiece> findByBrand(String brand);

    Optional<Eyepiece> findByBrandAndModel(String brand, String model);

    List<Eyepiece> findByBrandContainingIgnoreCase(String brand);

    List<Eyepiece> findByModelContainingIgnoreCase(String model);

    List<Eyepiece> findByFocalLength(String focalLength);

    List<Eyepiece> findByBarrelSize(String barrelSize);
}
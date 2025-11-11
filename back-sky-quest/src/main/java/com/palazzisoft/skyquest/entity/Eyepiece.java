package com.palazzisoft.skyquest.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "eyepiece", schema = "skyquest")
public class Eyepiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, name = "focal_length")
    private String focalLength;  // e.g., "25mm", "10mm"

    @Column(name = "apparent_fov")
    private String apparentFov;  // e.g., "82°", "68°"

    @Column(name = "barrel_size")
    private String barrelSize;  // e.g., "1.25\"", "2\""

    @Column(name = "eye_relief")
    private String eyeRelief;  // e.g., "20mm", "15mm"

    @Column(name = "field_stop")
    private String fieldStop;  // e.g., "27mm"

    private String weight;  // Optional field, e.g., "200g"

    private String price;  // Optional field, e.g., "$299"

    private String description;
}
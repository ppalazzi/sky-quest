package com.palazzisoft.skyquest.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "telescope", schema = "skyquest")
public class Telescope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TelescopeType type;

    @Column(nullable = false)
    private String aperture;  // e.g., "203mm (8 in)"

    @Column(nullable = false, name = "focal_length")
    private String focalLength;  // e.g., "2032mm"

    @Column(nullable = false, name = "focal_ratio")
    private String focalRatio;  // e.g., "f/10"

    @Column(name = "mount_type")
    private String mountType;

    private String weight;

    private String price;
}

package com.palazzisoft.skyquest.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "filter", schema = "skyquest")
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "filter_seq", sequenceName = "filter_seq")
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, name = "filter_type")
    private String filterType;  // e.g., "UHC", "OIII", "H-alpha", "Broadband", "Narrowband"

    @Column(name = "thread_size")
    private String threadSize;  // e.g., "1.25\"", "2\"", "M48"

    private String wavelength;  // e.g., "656nm", "500-501nm"

    private String transmission;  // e.g., "90%", "12nm FWHM"

    @Column(name = "optical_density")
    private String opticalDensity;  // e.g., "ND 0.6", "ND 3.8"

    private String application;  // e.g., "Deep Sky", "Planetary", "Solar", "Lunar"

    private String weight;  // Optional field, e.g., "50g"

    private String price;  // Optional field, e.g., "$149"

    private String description;
}
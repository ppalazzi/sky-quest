package com.palazzisoft.skyquest.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "camera", schema = "skyquest")
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "camera_seq", sequenceName = "camera_seq")
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(name = "sensor_type")
    private String sensorType;  // e.g., "CMOS", "CCD"

    private String resolution;  // e.g., "1920x1080", "4096x4096"

    @Column(name = "pixel_size")
    private String pixelSize;  // e.g., "2.4μm"

    @Column(name = "cooling_type")
    private String coolingType;  // e.g., "Air cooled", "TEC cooled"

    @Column(name = "interface_type")
    private String interfaceType;  // e.g., "USB 3.0", "USB-C"

    private String weight;  // Optional field, e.g., "500g"

    private String price;  // Optional field, e.g., "$899"

    private String description;
}
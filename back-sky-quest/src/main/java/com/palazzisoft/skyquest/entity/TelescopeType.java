package com.palazzisoft.skyquest.entity;

import lombok.Getter;

@Getter
public enum TelescopeType {

    REFRACTOR("refractor"),
    REFLECTOR("reflector"),
    SCHMIDT_CASSEGRAIN("schmidt-cassegrain"),
    MAKSUTOV_CASSEGRAIN("maksutov-cassegrain"),
    DOBSONIAN("dobsonian");

    private final String value;

    TelescopeType(String value) {
        this.value = value;
    }
}
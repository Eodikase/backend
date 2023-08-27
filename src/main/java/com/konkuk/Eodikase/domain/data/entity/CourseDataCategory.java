package com.konkuk.Eodikase.domain.data.entity;

public enum CourseDataCategory {
    CAFE("CAFE"),
    RES("RES"),
    ACT("ACT");

    private final String category;


    CourseDataCategory(final String category) {
        this.category = category;
    }

}

package com.konkuk.Eodikase.domain.data.entity;

import com.konkuk.Eodikase.exception.course.InvalidCourseDataCategoryException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum CourseDataCategory {
    CAFE("CAFE"),
    RES("RES"),
    ACT("ACT");

    private String value;

    public static CourseDataCategory from(String value) {
        return Arrays.stream(values())
                .filter(it -> Objects.equals(it.value, value))
                .findFirst()
                .orElseThrow(InvalidCourseDataCategoryException::new);
    }

}

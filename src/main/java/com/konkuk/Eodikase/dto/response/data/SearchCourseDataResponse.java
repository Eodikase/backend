package com.konkuk.Eodikase.dto.response.data;

import lombok.Getter;

@Getter
public class SearchCourseDataResponse {

    private Long id;

    private String name;

    public SearchCourseDataResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

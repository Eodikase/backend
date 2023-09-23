package com.konkuk.Eodikase.dto.response;

import lombok.Getter;

@Getter
public class FindAllCourseDataResponse {
    private Long id;
    private double lat;
    private double lng;

    public FindAllCourseDataResponse(Long id, double lat, double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }
}

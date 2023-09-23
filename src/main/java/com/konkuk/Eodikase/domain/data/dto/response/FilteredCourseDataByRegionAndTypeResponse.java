package com.konkuk.Eodikase.domain.data.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FilteredCourseDataByRegionAndTypeResponse {

    private Long id;

    private String name;

    private String category;

    private String location;

    private String phoneNumber;

    private Float scoreByNaver;

    private String operatingTime;

    private int reviewCount;

    private String imageUrl;

    private double lat;

    private double lng;

    public FilteredCourseDataByRegionAndTypeResponse(
            Long id, String name, String category, String location, String phoneNumber, Float scoreByNaver,
            String operatingTime, int reviewCount, String imageUrl, double lat, double lng
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.scoreByNaver = scoreByNaver;
        this.operatingTime = operatingTime;
        this.reviewCount = reviewCount;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lng = lng;
    }
}

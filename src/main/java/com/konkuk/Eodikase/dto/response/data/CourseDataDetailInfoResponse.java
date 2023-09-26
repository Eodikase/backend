package com.konkuk.Eodikase.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseDataDetailInfoResponse {
    private Long id;
    private String name;
    private String category;
    private String location;
    private String phoneNumber;
    private Float scoreByNaver;
    private String href;
    private String operatingTime;
    private int reviewCount;
    private String imageUrl;
    private String img1;
    private String img2;
    private String img3;
    private double lat;
    private double lng;

}

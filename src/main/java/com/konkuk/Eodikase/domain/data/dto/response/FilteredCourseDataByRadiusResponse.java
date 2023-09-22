package com.konkuk.Eodikase.domain.data.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FilteredCourseDataByRadiusResponse {

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

    private String addr;

    private Point point;

    public FilteredCourseDataByRadiusResponse(
            Long id, String name, String category, String location, String phoneNumber, Float scoreByNaver, String href,
            String operatingTime, int reviewCount, String imageUrl, String img1, String img2, String img3,
            double lat, double lng, String addr, Point point
            ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.scoreByNaver = scoreByNaver;
        this.href = href;
        this.operatingTime = operatingTime;
        this.reviewCount = reviewCount;
        this.imageUrl = imageUrl;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.lat = lat;
        this.lng = lng;
        this.addr = addr;
        this.point = point;
    }
}

package com.konkuk.Eodikase.domain.audit;

import lombok.Getter;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseCourseEntity {

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
}

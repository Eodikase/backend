package com.konkuk.Eodikase.domain.data.entity;

import com.konkuk.Eodikase.domain.course.entity.CourseCourseDataRel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
public class CourseData{

    @Id
    @GeneratedValue
    @Column(name="course_data_id")
    private Long id;

    private String name;

    private String location;

    private String locationDescription;

    private String locationTransit;

    private String category;

    private String operatingTime;

    private String phoneNum;

    private String description;

    private int scoreByNaver;

    private String menu;

    private String theme;

    //TODO 이미지 URL 형태로
    private String imageList;

    @OneToMany(mappedBy = "courseData")
    private List<CourseCourseDataRel> courseDataList = new ArrayList<>();

}
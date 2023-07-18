package com.konkuk.Eodikase.domain.data.entity;

import com.konkuk.Eodikase.domain.course.entity.CourseCourseDataRel;
import com.konkuk.Eodikase.domain.member.entity.MemberRole;
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
public abstract class CourseData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_data_id")
    private Long id;

    private String name;

    private String category;

    private String location;

    private String phoneNumber;

    private String scoreByNaver;

    private String href;

    private String locationDescription;

    private String locationTransit;

    private String operatingTime;

    private String description;

    private String menu;

    private String theme;

    //TODO 이미지 URL 형태로
    private String imageList;

    @Enumerated(value = EnumType.STRING)
    private CourseDataLocation courseDataLocation;

    @OneToMany(mappedBy = "courseData")
    private List<CourseCourseDataRel> courseDataList = new ArrayList<>();

}

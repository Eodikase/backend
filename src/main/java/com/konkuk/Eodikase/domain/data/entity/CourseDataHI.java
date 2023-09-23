package com.konkuk.Eodikase.domain.data.entity;

import com.konkuk.Eodikase.domain.audit.BaseCourseEntity;
import com.konkuk.Eodikase.domain.course.entity.CourseCourseDataRel;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
public class CourseDataHI extends BaseCourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_data_hi_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private CourseDataCategory courseDataCategory;

    @OneToMany(mappedBy = "courseDataHI")
    private List<CourseCourseDataRel> courseDataList = new ArrayList<>();

}

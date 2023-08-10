package com.konkuk.Eodikase.domain.data.entity;

import com.konkuk.Eodikase.domain.audit.BaseCourseEntity;
import com.konkuk.Eodikase.domain.course.entity.CourseCourseDataRel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
public class CourseDataHSE extends BaseCourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_data_hse_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private com.konkuk.Eodikase.domain.data.entity.CourseDataCategory CourseDataCategory;

    @OneToMany(mappedBy = "courseDataHSE")
    private List<CourseCourseDataRel> courseDataList = new ArrayList<>();
}
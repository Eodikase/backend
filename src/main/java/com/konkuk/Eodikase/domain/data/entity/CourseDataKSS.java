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
public class CourseDataKSS extends BaseCourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_data_kss_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private CourseDataCategory CourseDataCategory;

    @OneToMany(mappedBy = "courseDataKSS")
    private List<CourseCourseDataRel> courseDataList = new ArrayList<>();

}

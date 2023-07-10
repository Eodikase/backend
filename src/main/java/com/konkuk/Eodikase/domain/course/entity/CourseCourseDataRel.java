package com.konkuk.Eodikase.domain.course.entity;

import com.konkuk.Eodikase.domain.data.entity.CourseData;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "course_course_data_rel")
public class CourseCourseDataRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_coursedata_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_data_id")
    private CourseData courseData;

}

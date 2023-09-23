package com.konkuk.Eodikase.domain.course.entity;

import com.konkuk.Eodikase.domain.data.entity.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "course_course_data_rel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseCourseDataRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_coursedata_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    //코스에서 해당 데이터가 몇번째인지
    private int sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_data_em_id")
    private CourseDataEM courseDataEM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_data_hi_id")
    private CourseDataHI courseDataHI;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_data_hse_id")
    private CourseDataHSE courseDataHSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_data_kss_id")
    private CourseDataKSS courseDataKSS;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_data_ns_id")
    private CourseDataNS courseDataNS;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_data_sbg_id")
    private CourseDataSBG courseDataSBG;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_data_sh_id")
    private CourseDataSH courseDataSH;



    @Builder
    public CourseCourseDataRel(CourseDataEM courseDataEM, CourseDataHI courseDataHI, CourseDataHSE courseDataHSE,
                               CourseDataKSS courseDataKSS, CourseDataNS courseDataNS,CourseDataSBG courseDataSBG, CourseDataSH courseDataSH, int sequence )
    {
        this.courseDataEM = courseDataEM;
        this.courseDataHI = courseDataHI;
        this.courseDataHSE = courseDataHSE;
        this.courseDataKSS = courseDataKSS;
        this.courseDataNS = courseDataNS;
        this.courseDataSBG = courseDataSBG;
        this.courseDataSH = courseDataSH;
        this.sequence = sequence;
    }
    public void assignCourse(Course course){
        this.course = course;
        course.getCourseDataRelList().add(this);
    }
}

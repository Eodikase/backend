package com.konkuk.Eodikase.domain.course.repository;

import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.course.entity.CourseCourseDataRel;
import com.konkuk.Eodikase.domain.data.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CourseCourseDataRelRepository extends JpaRepository<CourseCourseDataRel,Long> {
    List<CourseCourseDataRel> findByCourseId(Long courseId);

    Page<Course> findCourseByCourseDataEMInOrCourseDataHIInOrCourseDataHSEInOrCourseDataNSInOrCourseDataKSSInOrCourseDataSBGInOrCourseDataSHIn(
            List<CourseDataEM> courseDataEMs,
            List<CourseDataHI> courseDataHIs,
            List<CourseDataHSE> courseDataHSEs,
            List<CourseDataKSS> courseDataKSSs,
            List<CourseDataNS> courseDataNSs,
            List<CourseDataSBG> courseDataSBGs,
            List<CourseDataSH> courseDataSHs,
            Pageable pageable
    );
}

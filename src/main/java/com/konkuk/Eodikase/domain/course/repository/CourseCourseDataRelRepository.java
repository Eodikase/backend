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

//    Page<CourseCourseDataRel> findByCourseDataEMInOrCourseDataHIInOrCourseDataHSEInOrCourseDataNSInOrCourseDataKSSInOrCourseDataSBGInOrCourseDataSHIn(
//            List<CourseDataEM> courseDataEMs,
//            List<CourseDataHI> courseDataHIs,
//            List<CourseDataHSE> courseDataHSEs,
//            List<CourseDataKSS> courseDataKSSs,
//            List<CourseDataNS> courseDataNSs,
//            List<CourseDataSBG> courseDataSBGs,
//            List<CourseDataSH> courseDataSHs,
//            Pageable pageable
//    );

    Page<CourseCourseDataRel> findByCourseDataEMNameContainingOrCourseDataHINameContainingOrCourseDataHSENameContainingOrCourseDataNSNameContainingOrCourseDataKSSNameContainingOrCourseDataSBGNameContainingOrCourseDataSHNameContaining
            (String keyword,String keyword2,String keyword3,String keyword4,String keyword5,String keyword6,String keyword7,Pageable pageable);

}

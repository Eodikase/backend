package com.konkuk.Eodikase.domain.course.repository;

import com.konkuk.Eodikase.domain.course.entity.CourseCourseDataRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CourseCourseDataRelRepository extends JpaRepository<CourseCourseDataRel,Long> {
    List<CourseCourseDataRel> findByCourseId(Long courseId);
}

package com.konkuk.Eodikase.domain.course.repository;

import com.konkuk.Eodikase.domain.course.entity.CourseCourseDataRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CourseCourseDataRelRepository extends JpaRepository<CourseCourseDataRel,Long> {
    @Query("select c.sequence from CourseCourseDataRel c where c.course.id = :courseId ORDER BY c.sequence DESC")
    List<Integer> findTopByCourseId(@Param("courseId") Long courseId);
}

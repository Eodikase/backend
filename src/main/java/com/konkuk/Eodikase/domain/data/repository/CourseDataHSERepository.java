package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataHSE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDataHSERepository extends JpaRepository<CourseDataHSE, Long> {
    List<CourseDataHSE> findByCourseDataCategory(CourseDataCategory category);
}

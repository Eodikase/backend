package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataHI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDataHIRepository extends JpaRepository<CourseDataHI, Long> {
    List<CourseDataHI> findByCourseDataCategory(CourseDataCategory category);
}

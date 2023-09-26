package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataHI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDataHIRepository extends JpaRepository<CourseDataHI, Long> {
    List<CourseDataHI> findByCourseDataCategory(CourseDataCategory category);

    Page<CourseDataHI> findByCourseDataCategoryAndNameContaining(CourseDataCategory category, String keyword, Pageable pageable);
}

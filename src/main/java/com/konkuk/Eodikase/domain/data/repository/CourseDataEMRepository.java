package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataEM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDataEMRepository extends JpaRepository<CourseDataEM, Long> {
    List<CourseDataEM> findByCourseDataCategory(CourseDataCategory category);

    Page<CourseDataEM> findByCourseDataCategoryAndNameContaining(CourseDataCategory category, String keyword, Pageable pageable);
}

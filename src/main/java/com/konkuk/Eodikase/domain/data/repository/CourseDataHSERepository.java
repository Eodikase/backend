package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataEM;
import com.konkuk.Eodikase.domain.data.entity.CourseDataHSE;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDataHSERepository extends JpaRepository<CourseDataHSE, Long> {

    long count();

    List<CourseDataHSE> findByCourseDataCategory(CourseDataCategory category);

    Page<CourseDataHSE> findByCourseDataCategoryAndNameContaining(CourseDataCategory category, String keyword, Pageable pageable);

    List<CourseDataHSE> findByNameContaining(String keyword);
}

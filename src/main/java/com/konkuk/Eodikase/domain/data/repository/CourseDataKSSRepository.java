package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataKSS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDataKSSRepository extends JpaRepository<CourseDataKSS, Long> {

    long count();

    List<CourseDataKSS> findByCourseDataCategory(CourseDataCategory category);

    Page<CourseDataKSS> findByCourseDataCategoryAndNameContaining(CourseDataCategory category, String keyword, Pageable pageable);
}

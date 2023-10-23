package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataSBG;
import com.konkuk.Eodikase.domain.data.entity.CourseDataSH;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDataSHRepository extends JpaRepository<CourseDataSH, Long> {

    long count();

    List<CourseDataSH> findByCourseDataCategory(CourseDataCategory category);

    Page<CourseDataSH> findByCourseDataCategoryAndNameContaining(CourseDataCategory category, String keyword, Pageable pageable);

    List<CourseDataSH> findByNameContaining(String keyword);
}

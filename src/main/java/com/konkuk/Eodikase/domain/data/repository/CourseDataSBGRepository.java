package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataSBG;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDataSBGRepository extends JpaRepository<CourseDataSBG, Long> {

    long count();

    List<CourseDataSBG> findByCourseDataCategory(CourseDataCategory category);

    Page<CourseDataSBG> findByCourseDataCategoryAndNameContaining(CourseDataCategory category, String keyword, Pageable pageable);
}

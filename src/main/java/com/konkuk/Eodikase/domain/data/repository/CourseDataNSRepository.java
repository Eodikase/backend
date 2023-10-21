package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataEM;
import com.konkuk.Eodikase.domain.data.entity.CourseDataNS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDataNSRepository extends JpaRepository<CourseDataNS, Long> {
    List<CourseDataNS> findByCourseDataCategory(CourseDataCategory category);

    Page<CourseDataNS> findByCourseDataCategoryAndNameContaining(CourseDataCategory category, String keyword, Pageable pageable);

    List<CourseDataNS> findByNameContaining(String keyword);
}

package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataKSS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDataKSSRepository extends JpaRepository<CourseDataKSS, Long> {
    List<CourseDataKSS> findByCourseDataCategory(CourseDataCategory category);
}

package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataSH;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDataSHRepository extends JpaRepository<CourseDataSH, Long> {
    List<CourseDataSH> findByCourseDataCategory(CourseDataCategory category);
}

package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataSBG;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDataSBGRepository extends JpaRepository<CourseDataSBG, Long> {
    List<CourseDataSBG> findByCourseDataCategory(CourseDataCategory category);
}

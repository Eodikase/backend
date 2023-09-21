package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDataRepository extends JpaRepository<CourseDataCategory, Long> {

}

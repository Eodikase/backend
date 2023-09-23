package com.konkuk.Eodikase.domain.course.repository;

import com.konkuk.Eodikase.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {



}

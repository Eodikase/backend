package com.konkuk.Eodikase.domain.course.repository;

import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.course.entity.CourseRegion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Page<Course> findAll(Pageable pageable);
    Page<Course> findAllByRegion(CourseRegion region, Pageable pageable);

}

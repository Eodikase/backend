package com.konkuk.Eodikase.domain.course.repository;

import com.konkuk.Eodikase.domain.course.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Page<Course> findAll(Pageable pageable);


}

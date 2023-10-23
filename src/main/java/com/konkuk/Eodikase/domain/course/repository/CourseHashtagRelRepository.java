package com.konkuk.Eodikase.domain.course.repository;

import com.konkuk.Eodikase.domain.course.entity.CourseCourseDataRel;
import com.konkuk.Eodikase.domain.course.entity.CourseHashtagRel;
import com.konkuk.Eodikase.domain.hashtag.entity.HashTagName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseHashtagRelRepository extends JpaRepository<CourseHashtagRel,Long> {
    Page<CourseHashtagRel> findByHashtagHashTagName(HashTagName tag, Pageable pageable);
}

package com.konkuk.Eodikase.domain.course.repository;

import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.course.entity.CourseRegion;
import com.konkuk.Eodikase.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Page<Course> findAllByIsOpen(Boolean open, Pageable pageable);
    Page<Course> findAllByRegionAndIsOpen(CourseRegion region,Boolean open, Pageable pageable);
    Page<Course> findAllByMemberAndIsOpen(Member member, Boolean open, Pageable pageable);
    @Query("SELECT c FROM Course c WHERE c.id IN :courseIds")
    Page<Course> findCoursesByIdIn(@Param("courseIds") List<Long> courseIds, Pageable pageable);
    void deleteByMember(Member member);
    Page<Course> findByCourseNameContainingOrCourseDescriptionContaining(String keywordN,String keywordD, Pageable pageable);
}

package com.konkuk.Eodikase.domain.like.repository;

import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.like.entity.Like;
import com.konkuk.Eodikase.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {

    Optional<Like> findByMemberAndCourse(Member member, Course course);
    Optional<Like> findByMemberIdAndCourseId(Long memberId, Long CourseId);
    void deleteByMember(Member member);
}

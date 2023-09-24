package com.konkuk.Eodikase.domain.bookmark.repository;

import com.konkuk.Eodikase.domain.bookmark.entity.Bookmark;
import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.like.entity.Like;
import com.konkuk.Eodikase.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {

    Optional<Bookmark> findByMemberAndCourse(Member member, Course course);
//    Optional<Bookmark> findByMemberIdAndCourseId(Long memberId, Long CourseId);
}

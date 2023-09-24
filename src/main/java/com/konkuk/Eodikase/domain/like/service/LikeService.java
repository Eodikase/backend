package com.konkuk.Eodikase.domain.like.service;

import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.course.repository.CourseRepository;
import com.konkuk.Eodikase.domain.like.entity.Like;
import com.konkuk.Eodikase.domain.like.repository.LikeRepository;
import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.exception.badrequest.LikeExistException;
import com.konkuk.Eodikase.exception.badrequest.LikeNotExistException;
import com.konkuk.Eodikase.exception.notfound.NotFoundCourseException;
import com.konkuk.Eodikase.exception.notfound.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    public void like(Long memberId, Long courseId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(NotFoundCourseException::new);

        // 이미 좋아요되어있으면 에러 반환
        if (likeRepository.findByMemberAndCourse(member, course).isPresent()){
            throw new LikeExistException();
        }

        Like like = Like.builder()
                .course(course)
                .member(member)
                .build();

        likeRepository.save(like);
    }
    public void cancel(Long memberId, Long courseId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(NotFoundCourseException::new);
        //
        Like like = likeRepository.findByMemberIdAndCourseId(memberId, courseId)
                .orElseThrow(() -> new LikeNotExistException());

        likeRepository.delete(like);
    }
}

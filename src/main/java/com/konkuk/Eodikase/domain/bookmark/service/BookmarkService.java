package com.konkuk.Eodikase.domain.bookmark.service;

import com.konkuk.Eodikase.domain.bookmark.entity.Bookmark;
import com.konkuk.Eodikase.domain.bookmark.repository.BookmarkRepository;
import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.course.repository.CourseRepository;
import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.exception.bookmark.BookmarkExistException;
import com.konkuk.Eodikase.exception.bookmark.BookmarkMyException;
import com.konkuk.Eodikase.exception.bookmark.BookmarkNotExistException;
import com.konkuk.Eodikase.exception.course.NotFoundCourseException;
import com.konkuk.Eodikase.exception.notfound.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    public void insert(Long memberId, Long courseId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(NotFoundCourseException::new);

        // 이미 북마크되어있으면 에러 반환
        if (bookmarkRepository.findByMemberAndCourse(member, course).isPresent()){
            throw new BookmarkExistException();
        }
        // 자신이 생성한 코스 북마크 시 에러 반환
        if(course.getMember() == member){
            throw new BookmarkMyException();
        }

        Bookmark bookemark = Bookmark.builder()
                .course(course)
                .member(member)
                .build();

        bookmarkRepository.save(bookemark);
    }

    public void delete(Long memberId, Long courseId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(NotFoundCourseException::new);
        //
        Bookmark bookemark = bookmarkRepository.findByMemberAndCourse(member, course)
                .orElseThrow(BookmarkNotExistException::new);

        bookmarkRepository.delete(bookemark);
    }

}

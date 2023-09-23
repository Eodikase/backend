package com.konkuk.Eodikase.domain.course.service;

import com.konkuk.Eodikase.dto.request.course.CourseDataPostRequest;
import com.konkuk.Eodikase.dto.request.course.CoursePostRequest;
import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.course.entity.CourseCourseDataRel;
import com.konkuk.Eodikase.domain.course.repository.CourseCourseDataRelRepository;
import com.konkuk.Eodikase.domain.course.repository.CourseRepository;
import com.konkuk.Eodikase.domain.data.entity.*;
import com.konkuk.Eodikase.domain.data.repository.*;
import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.exception.badrequest.InvalidRegionException;
import com.konkuk.Eodikase.exception.notfound.NotFoundCourseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final CourseCourseDataRelRepository courseCourseDataRelRepository;
    private final CourseDataEMRepository courseDataEMRepository;
    private final CourseDataHIRepository courseDataHIRepository;
    private final CourseDataHSERepository courseDataHSERepository;
    private final CourseDataKSSRepository courseDataKSSRepository;
    private final CourseDataNSRepository courseDataNSRepository;
    private final CourseDataSBGRepository courseDataSBGRepository;
    private final CourseDataSHRepository courseDataSHRepository;

    @Transactional
    public Long saveCourse(Long memberId, CoursePostRequest request) {

        Member member = memberRepository.findById(memberId).get();
        Course course = Course.builder()
                        .courseName(request.getCourseName())
                        .courseDescription(request.getCourseDescription()).build();
        course.assignMember(member);
        return courseRepository.save(course).getId();
    }
    @Transactional
    public Long saveData(Long memberId, Long courseId, CourseDataPostRequest request) {
        Member member = memberRepository.findById(memberId).get();
        Course course = courseRepository.findById(courseId).orElseThrow(NotFoundCourseException::new);
        CourseCourseDataRel courseCourseDataRel;
        if(request.getRegion().equals("EM")){
            CourseDataEM courseDataEM = courseDataEMRepository.findById(request.getCourseDataId()).orElseThrow(NotFoundCourseException::new);
            courseCourseDataRel = CourseCourseDataRel.builder()
                    .courseDataEM(courseDataEM)
                    .sequence(request.getSequence())
                    .build();
            courseCourseDataRel.assignCourse(course);
            return courseCourseDataRelRepository.save(courseCourseDataRel).getId();
        } else if (request.getRegion().equals("HI")) {
            CourseDataHI courseDataHI = courseDataHIRepository.findById(request.getCourseDataId()).orElseThrow(NotFoundCourseException::new);
            courseCourseDataRel = CourseCourseDataRel.builder()
                    .courseDataHI(courseDataHI)
                    .sequence(request.getSequence())
                    .build();
            courseCourseDataRel.assignCourse(course);
            return courseCourseDataRelRepository.save(courseCourseDataRel).getId();
        } else if (request.getRegion().equals("HSE")) {
            CourseDataHSE courseDataHSE = courseDataHSERepository.findById(request.getCourseDataId()).orElseThrow(NotFoundCourseException::new);
            courseCourseDataRel = CourseCourseDataRel.builder()
                    .courseDataHSE(courseDataHSE)
                    .sequence(request.getSequence())
                    .build();
            courseCourseDataRel.assignCourse(course);
            return courseCourseDataRelRepository.save(courseCourseDataRel).getId();
        } else if (request.getRegion().equals("KSS")) {
            CourseDataKSS courseDataKSS = courseDataKSSRepository.findById(request.getCourseDataId()).orElseThrow(NotFoundCourseException::new);
            courseCourseDataRel = CourseCourseDataRel.builder()
                    .courseDataKSS(courseDataKSS)
                    .sequence(request.getSequence())
                    .build();
            courseCourseDataRel.assignCourse(course);
            return courseCourseDataRelRepository.save(courseCourseDataRel).getId();
        } else if (request.getRegion().equals("NS")) {
            CourseDataNS courseDataNS = courseDataNSRepository.findById(request.getCourseDataId()).orElseThrow(NotFoundCourseException::new);
            courseCourseDataRel = CourseCourseDataRel.builder()
                    .courseDataNS(courseDataNS)
                    .sequence(request.getSequence())
                    .build();
            courseCourseDataRel.assignCourse(course);
            return courseCourseDataRelRepository.save(courseCourseDataRel).getId();
        } else if (request.getRegion().equals("SBG")) {
            CourseDataSBG courseDataSBG = courseDataSBGRepository.findById(request.getCourseDataId()).orElseThrow(NotFoundCourseException::new);
            courseCourseDataRel = CourseCourseDataRel.builder()
                    .courseDataSBG(courseDataSBG)
                    .sequence(request.getSequence())
                    .build();
            courseCourseDataRel.assignCourse(course);
            return courseCourseDataRelRepository.save(courseCourseDataRel).getId();
        } else if (request.getRegion().equals("SH")) {
            CourseDataSH courseDataSH = courseDataSHRepository.findById(request.getCourseDataId()).orElseThrow(NotFoundCourseException::new);
            courseCourseDataRel = CourseCourseDataRel.builder()
                    .courseDataSH(courseDataSH)
                    .sequence(request.getSequence())
                    .build();
            courseCourseDataRel.assignCourse(course);
            return courseCourseDataRelRepository.save(courseCourseDataRel).getId();
        }
        throw new InvalidRegionException();
    }



}

package com.konkuk.Eodikase.domain.course.service;

import com.konkuk.Eodikase.domain.bookmark.entity.Bookmark;
import com.konkuk.Eodikase.domain.course.entity.CourseHashtagRel;
import com.konkuk.Eodikase.domain.course.entity.CourseRegion;
import com.konkuk.Eodikase.domain.course.repository.CourseHashtagRelRepository;
import com.konkuk.Eodikase.domain.hashtag.entity.HashTagName;
import com.konkuk.Eodikase.domain.hashtag.entity.Hashtag;
import com.konkuk.Eodikase.domain.hashtag.repository.HashtagRepository;
import com.konkuk.Eodikase.dto.request.course.CoursePostRequest;
import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.course.entity.CourseCourseDataRel;
import com.konkuk.Eodikase.domain.course.repository.CourseCourseDataRelRepository;
import com.konkuk.Eodikase.domain.course.repository.CourseRepository;
import com.konkuk.Eodikase.domain.data.entity.*;
import com.konkuk.Eodikase.domain.data.repository.*;
import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.dto.response.course.CourseResponse;
import com.konkuk.Eodikase.exception.badrequest.InvalidRegionException;
import com.konkuk.Eodikase.exception.course.CourseDataCountException;
import com.konkuk.Eodikase.exception.course.CourseNotAuthorizedException;
import com.konkuk.Eodikase.exception.course.NotFoundCourseException;
import com.konkuk.Eodikase.exception.hashtag.NotFoundHashtagException;
import com.konkuk.Eodikase.exception.notfound.NotFoundMemberException;
import com.konkuk.Eodikase.exception.score.InvalidScoreException;
import com.konkuk.Eodikase.exception.score.NotMyCourseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    private final CourseHashtagRelRepository courseHashtagRelRepository;
    private final HashtagRepository hashtagRepository;

    @Transactional
    public Long saveCourse(Long memberId, CoursePostRequest request) {



        Member member = memberRepository.findById(memberId).get();
        Course course = Course.builder()
                        .courseName(request.getCourseName())
                        .courseDescription(request.getCourseDescription())
                        .region(request.getCourseRegion())
                        .isOpen(request.getIsOpen()).build();
        course.assignMember(member);
        Long courseId = courseRepository.save(course).getId();

        if(request.getCourseItemIds().size() < 2 || request.getCourseItemIds().size() > 10 ){
            throw new CourseDataCountException();
        }
        for(Long hashtagId : request.getHashTagNames()){
            Hashtag hashtag = hashtagRepository.findById(hashtagId).orElseThrow(NotFoundHashtagException::new);
            CourseHashtagRel courseHashtagRel = CourseHashtagRel.builder().hashtag(hashtag).build();
            courseHashtagRel.assignCourse(course);
            courseHashtagRelRepository.save(courseHashtagRel);
        }

        CourseCourseDataRel courseCourseDataRel;
        if(request.getCourseRegion().getRegion().equals("EM")){
            for(Long courseDataId :request.getCourseItemIds()){
                CourseDataEM courseDataEM = courseDataEMRepository.findById(courseDataId).orElseThrow(NotFoundCourseException::new);
                courseCourseDataRel = CourseCourseDataRel.builder()
                        .courseDataEM(courseDataEM)
                        .build();
                courseCourseDataRel.assignCourse(course);
                courseCourseDataRelRepository.save(courseCourseDataRel);
            }
        } else if (request.getCourseRegion().getRegion().equals("HI")) {
            for(Long courseDataId :request.getCourseItemIds()){
                CourseDataHI courseDataHI = courseDataHIRepository.findById(courseDataId).orElseThrow(NotFoundCourseException::new);
                courseCourseDataRel = CourseCourseDataRel.builder()
                        .courseDataHI(courseDataHI)
                        .build();
                courseCourseDataRel.assignCourse(course);
                courseCourseDataRelRepository.save(courseCourseDataRel);
            }
        } else if (request.getCourseRegion().getRegion().equals("HSE")) {
            for(Long courseDataId :request.getCourseItemIds()){
                CourseDataHSE courseDataHSE = courseDataHSERepository.findById(courseDataId).orElseThrow(NotFoundCourseException::new);
                courseCourseDataRel = CourseCourseDataRel.builder()
                        .courseDataHSE(courseDataHSE)
                        .build();
                courseCourseDataRel.assignCourse(course);
                courseCourseDataRelRepository.save(courseCourseDataRel);
            }
        } else if (request.getCourseRegion().getRegion().equals("KSS")) {
            for(Long courseDataId :request.getCourseItemIds()){
                CourseDataKSS courseDataKSS = courseDataKSSRepository.findById(courseDataId).orElseThrow(NotFoundCourseException::new);
                courseCourseDataRel = CourseCourseDataRel.builder()
                        .courseDataKSS(courseDataKSS)
                        .build();
                courseCourseDataRel.assignCourse(course);
                courseCourseDataRelRepository.save(courseCourseDataRel);
            }
        } else if (request.getCourseRegion().getRegion().equals("NS")) {
            for(Long courseDataId :request.getCourseItemIds()){
                CourseDataNS courseDataNS = courseDataNSRepository.findById(courseDataId).orElseThrow(NotFoundCourseException::new);
                courseCourseDataRel = CourseCourseDataRel.builder()
                        .courseDataNS(courseDataNS)
                        .build();
                courseCourseDataRel.assignCourse(course);
                courseCourseDataRelRepository.save(courseCourseDataRel);
            }
        } else if (request.getCourseRegion().getRegion().equals("SBG")) {
            for(Long courseDataId :request.getCourseItemIds()){
                CourseDataSBG courseDataSBG = courseDataSBGRepository.findById(courseDataId).orElseThrow(NotFoundCourseException::new);
                courseCourseDataRel = CourseCourseDataRel.builder()
                        .courseDataSBG(courseDataSBG)
                        .build();
                courseCourseDataRel.assignCourse(course);
                courseCourseDataRelRepository.save(courseCourseDataRel);
            }
        } else if (request.getCourseRegion().getRegion().equals("SH")) {
            for(Long courseDataId :request.getCourseItemIds()){
                CourseDataSH courseDataSH = courseDataSHRepository.findById(courseDataId).orElseThrow(NotFoundCourseException::new);
                courseCourseDataRel = CourseCourseDataRel.builder()
                        .courseDataSH(courseDataSH)
                        .build();
                courseCourseDataRel.assignCourse(course);
                courseCourseDataRelRepository.save(courseCourseDataRel);
            }
        }else {
            throw new InvalidRegionException();
        }
        return courseRepository.save(course).getId();
    }

    public Page<CourseResponse> getCourses(Pageable pageable) {
        Page<CourseResponse> map = courseRepository.findAllByIsOpen(true, pageable).map(CourseResponse::new);
        return map;
    }

    public Page<CourseResponse> getCoursesByRegion(CourseRegion courseRegion, Pageable pageable) {
        Page<CourseResponse> map = courseRepository.findAllByRegionAndIsOpen(courseRegion,true,pageable).map(CourseResponse::new);
        return map;

    }
    public Page<CourseResponse> getCoursesByMember(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).get();
        Page<CourseResponse> map = courseRepository.findAllByMemberAndIsOpen(member,true,pageable).map(CourseResponse::new);
        return map;
    }

    public CourseResponse getCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(NotFoundCourseException::new);
        CourseResponse response = new CourseResponse(course);
        return response;
    }
    @Transactional
    public void deleteCourse(Long courseId, Long memeberId) {
        Course course = courseRepository.findById(courseId).orElseThrow(NotFoundCourseException::new);
        if(course.getMember().getId() != memeberId) throw new CourseNotAuthorizedException();
        courseRepository.delete(course);
    }

    @Transactional
    public void insertScore(Long courseId, double score,Long memberId ) {
        if(score > 5 || score <0) throw new InvalidScoreException();
        Course course = courseRepository.findById(courseId).orElseThrow(NotFoundCourseException::new);
        if(course.getMember().getId() != memberId) throw new NotMyCourseException();
        course.updateScore(score);
    }

    public Page<CourseResponse> findScrapedCourses(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        List<Bookmark> bookmarkList = member.getBookmarkList();

        List<Long> courseIds = bookmarkList.stream()
                .map(bookmark -> bookmark.getCourse().getId())
                .collect(Collectors.toList());

        Page<CourseResponse> courses = courseRepository.findCoursesByIdIn(courseIds, pageable)
                .map(CourseResponse::new);

        return courses;
    }

    public Page<CourseResponse> searchByData(String keyword, Pageable pageable) {

        //TODO 어떤 로직을 가져야 최적화가 될지

//        List<CourseDataEM> courseDataEMs = courseDataEMRepository.findByNameContaining(keyword);
//        List<CourseDataHI> courseDataHIs = courseDataHIRepository.findByNameContaining(keyword);
//        List<CourseDataHSE> courseDataHSEs = courseDataHSERepository.findByNameContaining(keyword);
//        List<CourseDataKSS> courseDataKSSs = courseDataKSSRepository.findByNameContaining(keyword);
//        List<CourseDataNS> courseDataNSs = courseDataNSRepository.findByNameContaining(keyword);
//        List<CourseDataSBG> courseDataSBGs = courseDataSBGRepository.findByNameContaining(keyword);
//        List<CourseDataSH> courseDataSHs = courseDataSHRepository.findByNameContaining(keyword);
//
//        Page<CourseCourseDataRel> courseRels = courseCourseDataRelRepository.findByCourseDataEMInOrCourseDataHIInOrCourseDataHSEInOrCourseDataNSInOrCourseDataKSSInOrCourseDataSBGInOrCourseDataSHIn(
//                courseDataEMs,
//                courseDataHIs,
//                courseDataHSEs,
//                courseDataKSSs,
//                courseDataNSs,
//                courseDataSBGs,
//                courseDataSHs,
//                pageable
//        );

        Page<CourseCourseDataRel> courseDataRels = courseCourseDataRelRepository.findByCourseDataEMNameContainingOrCourseDataHINameContainingOrCourseDataHSENameContainingOrCourseDataNSNameContainingOrCourseDataKSSNameContainingOrCourseDataSBGNameContainingOrCourseDataSHNameContaining(
                keyword,
                keyword,
                keyword,
                keyword,
                keyword,
                keyword,
                keyword,
                pageable
        );

        Page<CourseResponse> map = courseDataRels.map(CourseCourseDataRel::getCourse).map(CourseResponse::new);
        return map;
    }

    public Page<CourseResponse> searchByTitle(String keyword, Pageable pageable) {
        Page<CourseResponse> map
                = courseRepository.findByCourseNameContainingOrCourseDescriptionContaining(keyword,keyword, pageable).map(CourseResponse::new);
        return map;
    }

    public Page<CourseResponse> searchByTag(HashTagName tag, Pageable pageable) {

        Page<CourseHashtagRel> hashtagRels = courseHashtagRelRepository.findByHashtagHashTagName(tag,pageable);

        Page<CourseResponse> map = hashtagRels.map(CourseHashtagRel::getCourse).map(CourseResponse::new);
        return map;
    }
}

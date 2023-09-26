package com.konkuk.Eodikase.dto.response.course;

import com.konkuk.Eodikase.domain.audit.BaseCourseEntity;
import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.course.entity.CourseCourseDataRel;
import com.konkuk.Eodikase.domain.course.entity.CourseHashtagRel;
import com.konkuk.Eodikase.domain.hashtag.entity.HashTagName;
import com.konkuk.Eodikase.domain.member.entity.MemberProfileImage;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class CourseResponse {
    private Long courseId;
    private String courseName;
    private String courseDescription;
    private String nickname;
    private MemberProfileImage memberProfileImage;
    private int bookmarkCount;
    private int likeCount;
    private Timestamp createdDate;
    private double courseScore;
    private List<CourseCourseDataRelResponse> courseDataList;
    private List<CourseHashtagRelResponse> hashtagList;




    public CourseResponse(Course course){
        this.courseId = course.getId();
        this.courseName = course.getCourseName();
        this.courseDescription = course.getCourseDescription();
        this.nickname = course.getMember().getNickname();
        this.memberProfileImage = course.getMember().getMemberProfileImage();
        this.bookmarkCount = course.getBookmarkList().size();
        this.courseDataList = course.getCourseDataRelList().stream().map(CourseCourseDataRelResponse::new).collect(Collectors.toList());
        this.likeCount = course.getLikeList().size();
        this.createdDate = course.getCreatedDate();
        this.courseScore = course.getCourseScore();
        this.hashtagList = course.getHashtagRelList().stream().map(CourseHashtagRelResponse::new).collect(Collectors.toList());

    }
    @Data
    static class CourseCourseDataRelResponse{
        private Long courseDataId;
        private String name;
        private String imageUrl;
        private String category;
        private String location;
        private Float scoreByNaver;
        private double lat;
        private double lng;




        public CourseCourseDataRelResponse(CourseCourseDataRel courseCourseDataRel){

            if (courseCourseDataRel.getCourseDataEM() != null) {
                this.courseDataId = courseCourseDataRel.getCourseDataEM().getId();
                updateFields(courseCourseDataRel.getCourseDataEM());
            }
            if (courseCourseDataRel.getCourseDataHI() != null) {
                this.courseDataId = courseCourseDataRel.getCourseDataHI().getId();
                updateFields(courseCourseDataRel.getCourseDataHI());
            }
            if (courseCourseDataRel.getCourseDataHSE() != null) {
                this.courseDataId = courseCourseDataRel.getCourseDataHSE().getId();
                updateFields(courseCourseDataRel.getCourseDataHSE());
            }
            if (courseCourseDataRel.getCourseDataKSS() != null) {
                this.courseDataId = courseCourseDataRel.getCourseDataKSS().getId();
                updateFields(courseCourseDataRel.getCourseDataKSS());
            }
            if (courseCourseDataRel.getCourseDataNS() != null) {
                this.courseDataId = courseCourseDataRel.getCourseDataNS().getId();
                updateFields(courseCourseDataRel.getCourseDataNS());
            }
            if (courseCourseDataRel.getCourseDataSBG() != null) {
                this.courseDataId = courseCourseDataRel.getCourseDataSBG().getId();
                updateFields(courseCourseDataRel.getCourseDataSBG());
            }
            if (courseCourseDataRel.getCourseDataSH() != null) {
                this.courseDataId = courseCourseDataRel.getCourseDataSH().getId();
                updateFields(courseCourseDataRel.getCourseDataSH());
            }
        }

        private void updateFields(BaseCourseEntity courseData) {
            this.name = courseData.getName();
            this.imageUrl = courseData.getImageUrl();
            this.category = courseData.getCategory();
            this.location = courseData.getLocation();
            this.scoreByNaver = courseData.getScoreByNaver();
            this.lat = courseData.getLat();
            this.lng = courseData.getLng();
        }
    }
    @Data
    private class CourseHashtagRelResponse {
        private HashTagName hashtagCode;
        private String hashtagName;

        public CourseHashtagRelResponse(CourseHashtagRel courseHashtagRel){
            this.hashtagCode = courseHashtagRel.getHashtag().getHashTagName();
            this.hashtagName = courseHashtagRel.getHashtag().getHashTagName().description();
        }

    }
}

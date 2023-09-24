package com.konkuk.Eodikase.dto.response.course;

import com.konkuk.Eodikase.domain.audit.BaseCourseEntity;
import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.course.entity.CourseCourseDataRel;
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
    private List<CourseCourseDataRelResponse> courseDataList;
    private Timestamp createdDate;


    public CourseResponse(Course course){
        this.courseId = course.getId();
        this.courseName = course.getCourseName();
        this.courseDescription = course.getCourseDescription();
        this.nickname = course.getMember().getNickname();
        this.memberProfileImage = course.getMember().getMemberProfileImage();
        this.bookmarkCount = course.getBookmarkList().size();
        this.courseDataList = course.getCourseDataRelList()
                .stream().map(CourseCourseDataRelResponse::new).collect(Collectors.toList());
        this.likeCount = course.getLikeList().size();
        this.createdDate = course.getCreatedDate();
    }
    @Data
    static class CourseCourseDataRelResponse{
        private String name;
        private String imageUrl;


        public CourseCourseDataRelResponse(CourseCourseDataRel courseCourseDataRel){
            if (courseCourseDataRel.getCourseDataEM() != null) {
                updateFields(courseCourseDataRel.getCourseDataEM());
            }
            if (courseCourseDataRel.getCourseDataHI() != null) {
                updateFields(courseCourseDataRel.getCourseDataHI());
            }
            if (courseCourseDataRel.getCourseDataHSE() != null) {
                updateFields(courseCourseDataRel.getCourseDataHSE());
            }
            if (courseCourseDataRel.getCourseDataKSS() != null) {
                updateFields(courseCourseDataRel.getCourseDataKSS());
            }
            if (courseCourseDataRel.getCourseDataNS() != null) {
                updateFields(courseCourseDataRel.getCourseDataNS());
            }
            if (courseCourseDataRel.getCourseDataSBG() != null) {
                updateFields(courseCourseDataRel.getCourseDataSBG());
            }
            if (courseCourseDataRel.getCourseDataSH() != null) {
                updateFields(courseCourseDataRel.getCourseDataSH());
            }
        }

        private void updateFields(BaseCourseEntity courseData) {
            this.name = courseData.getName();
            this.imageUrl = courseData.getImageUrl();
        }
    }
}

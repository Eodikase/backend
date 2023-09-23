package com.konkuk.Eodikase.domain.course.entity;

import com.konkuk.Eodikase.domain.audit.BaseEntity;
import com.konkuk.Eodikase.domain.bookmark.entity.Bookmark;
import com.konkuk.Eodikase.domain.comment.entity.Comment;
import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.review.entity.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "course")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_id")
    private Long id;

    private String courseName;

    private String courseDescription;

    //코스 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "course")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<CourseHashtagRel> hashtagList = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<CourseCourseDataRel> courseDataRelList = new ArrayList<>();

    public void assignMember(Member member){
        this.member = member;
        member.getCourseList().add(this);
    }

    public void addCourseDataList(CourseCourseDataRel courseDataRel){
        this.courseDataRelList.add(courseDataRel);
        courseDataRel.assignCourse(this);
    }

    public static Course createCourse(Member member, CourseCourseDataRel... courseDataRelList) {
        Course course = new Course();
        course.assignMember(member);
        for (CourseCourseDataRel courseDataRel : courseDataRelList) {
            course.addCourseDataList(courseDataRel);
        }
        return course;
    }
}

package com.konkuk.Eodikase.domain.member.entity;

import com.konkuk.Eodikase.domain.audit.BaseEntity;
import com.konkuk.Eodikase.domain.bookmark.entity.Bookmark;
import com.konkuk.Eodikase.domain.comment.entity.Comment;
import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.review.entity.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    private String nickname;

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Course> courseList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviewList = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private MemberStatus status;

    @Enumerated(value = EnumType.STRING)
    private MemberPlatform platform;

    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    public Member(String email, String password, String nickname, MemberPlatform platform) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.status = MemberStatus.MEMBER_ACTIVE;
        this.role = MemberRole.USER;
        this.platform = platform;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}

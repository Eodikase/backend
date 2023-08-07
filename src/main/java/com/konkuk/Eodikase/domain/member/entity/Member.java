package com.konkuk.Eodikase.domain.member.entity;

import com.konkuk.Eodikase.domain.audit.BaseEntity;
import com.konkuk.Eodikase.domain.bookmark.entity.Bookmark;
import com.konkuk.Eodikase.domain.comment.entity.Comment;
import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.review.entity.Review;
import com.konkuk.Eodikase.exception.badrequest.InvalidNicknameException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    private static final Pattern NICKNAME_REGEX = Pattern.compile("^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]{2,8}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", length = 100)
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

    @Column(name = "platform_id")
    private String platformId;

    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    @OneToOne
    @JoinColumn(name = "member_profile_image_id")
    private MemberProfileImage memberProfileImage;

    public Member(String email, String password, String nickname, MemberPlatform platform,
                  MemberProfileImage memberProfileImage) {
        validateNickname(nickname);
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.status = MemberStatus.MEMBER_ACTIVE;
        this.role = MemberRole.USER;
        this.platform = platform;
        this.platformId = null;
        this.memberProfileImage = memberProfileImage;
    }

    public Member(String email, MemberPlatform platform, String platformId) {
        this.email = email;
        this.status = MemberStatus.MEMBER_ACTIVE;
        this.platform = platform;
        this.platformId = platformId;
    }

    public void registerOAuthMember(String email, String nickname) {
        validateNickname(nickname);
        this.nickname = nickname;
        if (email != null) {
            this.email = email;
        }
    }

    private void validateNickname(String nickname) {
        if (!NICKNAME_REGEX.matcher(nickname).matches()) {
            throw new InvalidNicknameException();
        }
    }

    public void updateProfileInfo(String nickname) {
        validateNickname(nickname);
        this.nickname = nickname;
    }

    private void updateBeforeProfileImageNotUsedStatus() {
        if (this.memberProfileImage != null) {
            this.memberProfileImage.updateNotUsedStatus();
        }
    }

    public void updateProfileImgUrl(MemberProfileImage memberProfileImage) {
        updateBeforeProfileImageNotUsedStatus();
        this.memberProfileImage = memberProfileImage;
    }

    public String getImgUrl() {
        return this.memberProfileImage != null ? this.memberProfileImage.getImgUrl() : null;
    }
}

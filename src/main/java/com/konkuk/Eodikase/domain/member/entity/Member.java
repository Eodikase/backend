package com.konkuk.Eodikase.domain.member.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String phoneNum;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    private MemberPlatform platform;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}

package com.konkuk.Eodikase.domain.member.entity;

/**
 * 회원가입 - MEMBER_ACTIVE
 * 1년이상 로그인 x - MEMBER_SLEEP
 * 회원탈퇴 - MEMBER_QUIT
 */
public enum MemberStatus {
    MEMBER_ACTIVE("MEMBER_ACTIVE"),
    MEMBER_SLEEP("MEMBER_SLEEP"),
    MEMBER_QUIT("MEMBER_QUIT");

    private final String status;

    MemberStatus(final String status) {
        this.status = status;
    }
}

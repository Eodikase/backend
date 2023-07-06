package com.konkuk.Eodikase.domain.member.entity;

public enum MemberPlatform {
    HOME("HOME"),
    KAKAO("KAKAO"),
    NAVER("NAVER"),
    GOOGLE("GOOGLE");

    private final String value;

    MemberPlatform(final String value) {
        this.value = value;
    }
}

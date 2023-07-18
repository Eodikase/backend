package com.konkuk.Eodikase.domain.member.entity;

public enum MemberPlatform {
    //TODO OAUTH2 구현목록 회의 필요
    HOME("HOME"),
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE");

    private final String value;

    MemberPlatform(final String value) {
        this.value = value;
    }
}

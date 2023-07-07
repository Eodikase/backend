package com.konkuk.Eodikase.domain.member.entity;

public enum MemberRole {

    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    MemberRole(final String value) {
        this.value = value;
    }
}

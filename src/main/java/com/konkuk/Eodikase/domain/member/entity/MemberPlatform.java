package com.konkuk.Eodikase.domain.member.entity;

import com.konkuk.Eodikase.exception.badrequest.InvalidMemberPlatformException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum MemberPlatform {

    HOME("HOME"),
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE");

    private String value;

    public static MemberPlatform from(String value) {
        return Arrays.stream(values())
                .filter(it -> Objects.equals(it.value, value))
                .findFirst()
                .orElseThrow(InvalidMemberPlatformException::new);
    }
}

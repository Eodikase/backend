package com.konkuk.Eodikase.dto.response.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberPageResponse {

    private String email;
    private String nickname;
    private String imgUrl;
    private String intro;
}

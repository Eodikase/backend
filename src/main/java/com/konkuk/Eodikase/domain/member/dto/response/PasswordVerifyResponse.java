package com.konkuk.Eodikase.domain.member.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class PasswordVerifyResponse {
    private Boolean isSuccess;
}
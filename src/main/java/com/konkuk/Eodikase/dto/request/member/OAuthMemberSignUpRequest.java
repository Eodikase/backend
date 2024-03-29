package com.konkuk.Eodikase.dto.request.member;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class OAuthMemberSignUpRequest {

    private String email;

    @NotBlank(message = "1005:공백일 수 없습니다.")
    private String nickname;

    @NotBlank(message = "1005:공백일 수 없습니다.")
    private String platform;

    @NotBlank(message = "1005:공백일 수 없습니다.")
    private String platformId;
}

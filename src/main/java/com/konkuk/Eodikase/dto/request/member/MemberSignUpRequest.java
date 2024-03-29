package com.konkuk.Eodikase.dto.request.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MemberSignUpRequest {

    @Email(message = "1004:이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "1005:공백일 수 없습니다.")
    private String email;

    @NotBlank(message = "1005:공백일 수 없습니다.")
    private String password;

    @NotBlank(message = "1005:공백일 수 없습니다.")
    private String nickname;
}

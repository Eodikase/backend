package com.konkuk.Eodikase.dto.response.member;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class GetUpdateProfileInfoResponse {
    private String email;

    private String nickname;
}

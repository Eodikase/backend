package com.konkuk.Eodikase.dto.response.auth;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class OAuthTokenResponse {

    private String token;
    private String email;
    private Boolean isRegistered;
    private String platformId;
}

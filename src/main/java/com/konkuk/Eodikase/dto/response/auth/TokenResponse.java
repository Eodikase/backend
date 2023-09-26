package com.konkuk.Eodikase.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenResponse {
    private String token;

    public static TokenResponse from(final String token) {
        return new TokenResponse(token);
    }
}

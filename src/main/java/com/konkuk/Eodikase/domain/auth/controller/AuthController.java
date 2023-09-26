package com.konkuk.Eodikase.domain.auth.controller;

import com.konkuk.Eodikase.domain.auth.service.AuthService;
import com.konkuk.Eodikase.dto.request.AuthLoginRequest;
import com.konkuk.Eodikase.dto.request.KakaoLoginRequest;
import com.konkuk.Eodikase.dto.response.OAuthTokenResponse;
import com.konkuk.Eodikase.dto.response.Response;
import com.konkuk.Eodikase.dto.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Login", description = "인증")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/login")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "이메일 로그인")
    @PostMapping
    public Response<?> login(@RequestBody @Valid AuthLoginRequest request) {
        TokenResponse response = authService.login(request);
        return Response.ofSuccess("OK", response.getToken());
    }

    @Operation(summary = "카카오 OAuth 로그인")
    @PostMapping("/kakao")
    public Response<?> loginKakao(@RequestBody @Valid KakaoLoginRequest request) {
        OAuthTokenResponse response = authService.kakaoOAuthLogin(request);
        return Response.ofSuccess("OK", response);
    }
}

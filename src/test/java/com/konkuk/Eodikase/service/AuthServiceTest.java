package com.konkuk.Eodikase.service;

import com.konkuk.Eodikase.domain.auth.dto.request.AuthLoginRequest;
import com.konkuk.Eodikase.domain.auth.dto.response.TokenResponse;
import com.konkuk.Eodikase.domain.auth.service.AuthService;
import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.entity.MemberPlatform;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.exception.badrequest.PasswordMismatchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthService authService;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @DisplayName("회원 로그인 요청이 옳다면 토큰을 발급한다")
    @Test
    void login() {
        String email = "dlawotn3@naver.com";
        String password = "edks1234!";
        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member("dlawotn3@naver.com", encodedPassword, "감자", MemberPlatform.HOME,
                null);
        memberRepository.save(member);
        AuthLoginRequest loginRequest = new AuthLoginRequest(email, password);

        TokenResponse tokenResponse = authService.login(loginRequest);

        assertNotNull(tokenResponse.getToken());
    }

    @DisplayName("회원 로그인 요청이 올바르지 않다면 예외가 발생한다")
    @Test
    void loginWithException() {
        String email = "dlawotn3@naver.com";
        String password = "edks1234!";
        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member("dlawotn3@naver.com", encodedPassword, "감자", MemberPlatform.HOME,
                null);
        memberRepository.save(member);

        AuthLoginRequest loginRequest = new AuthLoginRequest(email, "wrongPassword");

        assertThrows(PasswordMismatchException.class,
                () -> authService.login(loginRequest));
    }
}

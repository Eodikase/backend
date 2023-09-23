package com.konkuk.Eodikase.service;

import com.konkuk.Eodikase.dto.request.member.AuthLoginRequest;
import com.konkuk.Eodikase.dto.request.member.KakaoLoginRequest;
import com.konkuk.Eodikase.dto.response.OAuthTokenResponse;
import com.konkuk.Eodikase.dto.response.TokenResponse;
import com.konkuk.Eodikase.domain.auth.service.AuthService;
import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.entity.MemberPlatform;
import com.konkuk.Eodikase.domain.member.entity.MemberStatus;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.exception.badrequest.PasswordMismatchException;
import com.konkuk.Eodikase.exception.unauthorized.InactiveMemberException;
import com.konkuk.Eodikase.security.auth.OAuthPlatformMemberResponse;
import com.konkuk.Eodikase.security.auth.kakao.KakaoOAuthUserProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ServiceTest
class AuthServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthService authService;

    @MockBean
    private KakaoOAuthUserProvider kakaoOAuthUserProvider;

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

    @Test
    @DisplayName("status가 QUIT인 회원이 자체 로그인 시도할 시 예외를 반환한다")
    void loginWithQuitStatus() {
        String email = "dlawotn3@naver.com";
        String password = "edks123!";
        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member("dlawotn3@naver.com", encodedPassword, "메리", MemberPlatform.HOME,
                null, MemberStatus.MEMBER_QUIT);
        memberRepository.save(member);
        AuthLoginRequest loginRequest = new AuthLoginRequest(email, password);

        assertThrows(InactiveMemberException.class,
                () -> authService.login(loginRequest));
    }

    @Test
    @DisplayName("Kakao OAuth 로그인 시 가입되지 않은 회원일 경우 이메일 값을 보내고 isRegistered 값을 false로 보낸다")
    void loginKakaoOAuthNotRegistered() {
        String expected = "dlawotn3@kakao.com";
        String platformId = "1234321";
        when(kakaoOAuthUserProvider.getKakaoPlatformMember(anyString()))
                .thenReturn(new OAuthPlatformMemberResponse(platformId, expected));

        OAuthTokenResponse actual = authService.kakaoOAuthLogin(new KakaoLoginRequest("token"));

        assertAll(
                () -> assertThat(actual.getToken()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected),
                () -> assertThat(actual.getIsRegistered()).isFalse(),
                () -> assertThat(actual.getPlatformId()).isEqualTo(platformId)
        );
    }

    @Test
    @DisplayName("Kakao OAuth 로그인 시 이미 가입된 회원일 경우 토큰과 이메일, 그리고 isRegistered 값을 true로 보낸다")
    void loginKakaoOAuthRegisteredAndMocacongMember() {
        String expected = "dlawotn3@kakao.com";
        String platformId = "1234321";
        Member member = new Member(
                expected,
                null,
                "메리",
                null,
                MemberPlatform.KAKAO,
                platformId
        );
        memberRepository.save(member);
        when(kakaoOAuthUserProvider.getKakaoPlatformMember(anyString()))
                .thenReturn(new OAuthPlatformMemberResponse(platformId, expected));

        OAuthTokenResponse actual = authService.kakaoOAuthLogin(new KakaoLoginRequest("token"));

        assertAll(
                () -> assertThat(actual.getToken()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected),
                () -> assertThat(actual.getIsRegistered()).isTrue(),
                () -> assertThat(actual.getPlatformId()).isEqualTo(platformId)
        );
    }

    @Test
    @DisplayName("Kakao OAuth 로그인 시 등록은 완료됐지만 회원가입 절차에 실패해 닉네임이 없으면 isRegistered 값을 false로 보낸다")
    void loginKakaoOAuthRegisteredButNotMocacongMember() {
        String expected = "dlawotn3@kakao.com";
        String platformId = "1234321";
        Member member = new Member("dlawotn3@kakao.com", MemberPlatform.KAKAO, "1234321");
        memberRepository.save(member);
        when(kakaoOAuthUserProvider.getKakaoPlatformMember(anyString()))
                .thenReturn(new OAuthPlatformMemberResponse(platformId, expected));

        OAuthTokenResponse actual = authService.kakaoOAuthLogin(new KakaoLoginRequest("token"));

        assertAll(
                () -> assertThat(actual.getToken()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected),
                () -> assertThat(actual.getIsRegistered()).isFalse(),
                () -> assertThat(actual.getPlatformId()).isEqualTo(platformId)
        );
    }
}

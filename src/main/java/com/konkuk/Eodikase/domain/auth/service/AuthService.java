package com.konkuk.Eodikase.domain.auth.service;

import com.konkuk.Eodikase.dto.request.AuthLoginRequest;
import com.konkuk.Eodikase.dto.request.KakaoLoginRequest;
import com.konkuk.Eodikase.dto.response.OAuthTokenResponse;
import com.konkuk.Eodikase.dto.response.TokenResponse;
import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.entity.MemberPlatform;
import com.konkuk.Eodikase.domain.member.entity.MemberStatus;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.exception.badrequest.PasswordMismatchException;
import com.konkuk.Eodikase.exception.notfound.NotFoundMemberException;
import com.konkuk.Eodikase.exception.unauthorized.InactiveMemberException;
import com.konkuk.Eodikase.security.auth.JwtTokenProvider;
import com.konkuk.Eodikase.security.auth.OAuthPlatformMemberResponse;
import com.konkuk.Eodikase.security.auth.kakao.KakaoOAuthUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final KakaoOAuthUserProvider kakaoOAuthUserProvider;

    public TokenResponse login(AuthLoginRequest request) {
        Member findMember = memberRepository.findByEmailAndPlatform(request.getEmail(), MemberPlatform.HOME)
                .orElseThrow(NotFoundMemberException::new);
        validatePassword(findMember, request.getPassword());
        validateStatus(findMember);

        String token = issueToken(findMember);
        return TokenResponse.from(token);
    }

    private String issueToken(final Member findMember) {
        return jwtTokenProvider.createToken(findMember.getId());
    }

    private void validatePassword(final Member findMember, final String password) {
        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new PasswordMismatchException();
        }
    }

    private void validateStatus(final Member member) {
        if (member.getStatus() != MemberStatus.MEMBER_ACTIVE) {
            throw new InactiveMemberException();
        }
    }

    public OAuthTokenResponse kakaoOAuthLogin(KakaoLoginRequest request) {
        OAuthPlatformMemberResponse kakaoPlatformMember =
                kakaoOAuthUserProvider.getKakaoPlatformMember(request.getToken());
        return generateOAuthTokenResponse(
                MemberPlatform.KAKAO,
                kakaoPlatformMember.getEmail(),
                kakaoPlatformMember.getPlatformId()
        );
    }

    private OAuthTokenResponse generateOAuthTokenResponse(MemberPlatform platform, String email, String platformId) {
        return memberRepository.findIdByPlatformAndPlatformId(platform, platformId)
                .map(memberId -> {
                    Member findMember = memberRepository.findById(memberId)
                            .orElseThrow(NotFoundMemberException::new);
                    validateStatus(findMember);
                    String token = issueToken(findMember);
                    // OAuth 로그인은 성공했지만 회원가입에 실패한 경우
                    if (!findMember.isRegisteredOAuthMember()) {
                        return new OAuthTokenResponse(token, findMember.getEmail(), false, platformId);
                    }
                    return new OAuthTokenResponse(token, findMember.getEmail(), true, platformId);
                })
                .orElseGet(() -> {
                    Member oauthMember = new Member(email, platform, platformId, MemberStatus.MEMBER_ACTIVE);
                    Member savedMember = memberRepository.save(oauthMember);
                    String token = issueToken(savedMember);
                    return new OAuthTokenResponse(token, email, false, platformId);
                });
    }
}

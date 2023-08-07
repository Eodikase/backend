package com.konkuk.Eodikase.domain.auth.service;

import com.konkuk.Eodikase.domain.auth.dto.request.AuthLoginRequest;
import com.konkuk.Eodikase.domain.auth.dto.response.TokenResponse;
import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.entity.MemberPlatform;
import com.konkuk.Eodikase.domain.member.entity.MemberStatus;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.exception.badrequest.PasswordMismatchException;
import com.konkuk.Eodikase.exception.notfound.NotFoundMemberException;
import com.konkuk.Eodikase.exception.unauthorized.InactiveMemberException;
import com.konkuk.Eodikase.security.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

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
}

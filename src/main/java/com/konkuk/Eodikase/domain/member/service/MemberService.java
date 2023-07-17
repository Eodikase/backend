package com.konkuk.Eodikase.domain.member.service;

import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.entity.MemberPlatform;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.domain.member.dto.request.MemberSignUpRequest;
import com.konkuk.Eodikase.domain.member.dto.response.MemberSignUpResponse;
import com.konkuk.Eodikase.exception.badrequest.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberSignUpResponse signUp(MemberSignUpRequest request) {
        validateDuplicateMember(request);

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = new Member(request.getEmail(), encodedPassword, request.getNickname());
        return new MemberSignUpResponse(memberRepository.save(member).getId());
    }

    private void validateDuplicateMember(MemberSignUpRequest memberSignUpRequest) {
        memberRepository.findByEmailAndPlatform(memberSignUpRequest.getEmail(), MemberPlatform.HOME)
                .ifPresent(member -> {
                    throw new DuplicateMemberException();
                });
    }
}

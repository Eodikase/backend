package com.konkuk.Eodikase.domain.member.service;

import com.konkuk.Eodikase.domain.member.dto.request.MemberProfileUpdateRequest;
import com.konkuk.Eodikase.domain.member.dto.request.MemberSignUpRequest;
import com.konkuk.Eodikase.domain.member.dto.request.OAuthMemberSignUpRequest;
import com.konkuk.Eodikase.domain.member.dto.request.PasswordVerifyRequest;
import com.konkuk.Eodikase.domain.member.dto.response.*;
import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.entity.MemberPlatform;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.exception.badrequest.*;
import com.konkuk.Eodikase.exception.notfound.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final Pattern PASSWORD_REGEX = Pattern
            .compile("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}");

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberSignUpResponse signUp(MemberSignUpRequest request) {
        validateDuplicateMember(request);
        validatePassword(request.getPassword());
        validateNickname(request.getNickname());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = new Member(request.getEmail(), encodedPassword, request.getNickname(), MemberPlatform.HOME,
                null);
        return new MemberSignUpResponse(memberRepository.save(member).getId());
    }

    private void validateDuplicateMember(MemberSignUpRequest memberSignUpRequest) {
        if (memberRepository.existsByEmailAndPlatform(memberSignUpRequest.getEmail(), MemberPlatform.HOME)) {
            throw new DuplicateMemberException();
        }
        validateDuplicateNickname(memberSignUpRequest.getNickname());
    }

    private void validateNickname(String nickname) {
        if (nickname.isBlank()) {
            throw new InvalidNicknameException();
        }
    }

    private void validateDuplicateNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname))
            throw new DuplicateNicknameException();
    }

    private void validatePassword(String password) {
        if (!PASSWORD_REGEX.matcher(password).matches()) {
            throw new InvalidPasswordException();
        }
    }

    public IsDuplicateEmailResponse isDuplicateEmail(String email) {
        validateEmail(email);

        boolean existsEmail = memberRepository.existsByEmailAndPlatform(email, MemberPlatform.HOME);
        return new IsDuplicateEmailResponse(existsEmail);
    }

    private void validateEmail(String email) {
        if (email.isBlank()) {
            throw new InvalidEmailException();
        }
    }

    public IsDuplicateNicknameResponse isDuplicateNickname(String nickname) {
        validateNickname(nickname);

        boolean existsNickname = memberRepository.existsByNickname(nickname);
        return new IsDuplicateNicknameResponse(existsNickname);
    }

    @Transactional
    public OAuthMemberSignUpResponse signUpByOAuthMember(OAuthMemberSignUpRequest request) {
        MemberPlatform platform = MemberPlatform.from(request.getPlatform());
        Member member = memberRepository.findByPlatformAndPlatformId(platform, request.getPlatformId())
                .orElseThrow(NotFoundMemberException::new);

        member.registerOAuthMember(request.getEmail(), request.getNickname());
        return new OAuthMemberSignUpResponse(member.getId());
    }

    @Transactional
    public void updateProfileInfo(Long memberId, MemberProfileUpdateRequest request) {
        String updateNickname = request.getNickname();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        validateDuplicateNickname(updateNickname);
        member.updateProfileInfo(updateNickname);
    }

    public PasswordVerifyResponse verifyPassword(Long memberId, PasswordVerifyRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        String storedPassword = member.getPassword();
        Boolean isSuccess = passwordEncoder.matches(request.getPassword(), storedPassword);

        return new PasswordVerifyResponse(isSuccess);
    }

    public GetUpdateProfileInfoResponse getUpdateProfileInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        return new GetUpdateProfileInfoResponse(member.getEmail(), member.getNickname());
    }
}

package com.konkuk.Eodikase.domain.member.service;

import com.konkuk.Eodikase.domain.course.repository.CourseRepository;
import com.konkuk.Eodikase.domain.member.entity.*;
import com.konkuk.Eodikase.dto.request.member.MemberProfileUpdateRequest;
import com.konkuk.Eodikase.dto.request.member.MemberSignUpRequest;
import com.konkuk.Eodikase.dto.request.member.ResetPasswordRequest;
import com.konkuk.Eodikase.dto.request.member.OAuthMemberSignUpRequest;
import com.konkuk.Eodikase.dto.request.member.PasswordVerifyRequest;
import com.konkuk.Eodikase.domain.member.repository.MemberProfileImageRepository;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.dto.response.member.*;
import com.konkuk.Eodikase.exception.badrequest.*;
import com.konkuk.Eodikase.exception.notfound.NotFoundMemberException;
import com.konkuk.Eodikase.support.AwsS3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final Pattern PASSWORD_REGEX = Pattern
            .compile("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}");

    private final MemberRepository memberRepository;
    private final MemberProfileImageRepository memberProfileImageRepository;
    private final PasswordEncoder passwordEncoder;
    private final AwsS3Uploader awsS3Uploader;
    private final CourseRepository courseRepository;

    public MemberSignUpResponse signUp(MemberSignUpRequest request) {
        validateDuplicateMember(request);
        validatePassword(request.getPassword());
        validateNickname(request.getNickname());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = new Member(request.getEmail(), encodedPassword, request.getNickname(), MemberPlatform.HOME,
                null);
        if (request.getEmail().equals("dnjstjr245@naver.com")) {
            member.setAdminRole();
        }
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
        if (request.getEmail().equals("dnjstjr245@naver.com")) {
            member.setAdminRole();
        }
        return new OAuthMemberSignUpResponse(member.getId());
    }

    @Transactional
    public void updateProfileInfo(Long memberId, MemberProfileUpdateRequest request) {
        String updateNickname = request.getNickname();
        String updateIntro = request.getIntro();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        validateDuplicateNickname(updateNickname);
        validIntro(updateIntro);
        member.updateProfileInfo(updateNickname, updateIntro);
    }

    private void validIntro(String intro) {
        if (intro.length() > 150) {
            throw new InvalidIntroException();
        }
    }

    @Transactional
    public void resetPassword(Long memberId, ResetPasswordRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        String updatePassword = request.getPassword();
        validatePassword(updatePassword);
        String encryptedPassword = passwordEncoder.encode(updatePassword);
        member.updatePassword(encryptedPassword);
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

        return new GetUpdateProfileInfoResponse(member.getEmail(), member.getNickname(), member.getIntro());
    }

    @Transactional
    public void delete(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        if (findMember.getRole() == MemberRole.USER) {
            findMember.deleteMemberInfo();
        } else if (findMember.getRole() == MemberRole.ADMIN) {
            memberRepository.deleteById(memberId);
        }
    }

    @Transactional
    public void deleteMemberAfter30Days() {
        LocalDate thresholdLocalDate = LocalDate.now().minusDays(30);
        Instant instant = thresholdLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date thresholdDate = Date.from(instant);
        memberRepository.findMemberByCreatedTime(thresholdDate, MemberStatus.MEMBER_QUIT)
                .forEach(m -> courseRepository.deleteByMember(m));
        memberRepository.deleteMemberByCreatedTime(thresholdDate, MemberStatus.MEMBER_QUIT);
    }

    public MyPageResponse findMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        return new MyPageResponse(member.getEmail(), member.getNickname(), member.getImgUrl(), member.getIntro());
    }

    public MemberPageResponse findMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        return new MemberPageResponse(member.getEmail(), member.getNickname(), member.getImgUrl(), member.getIntro());
    }

    @Transactional
    public void updateProfileImage(Long memberId, MultipartFile profileImg) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        if (profileImg == null) {
            member.updateProfileImgUrl(null);
            return;
        }
        String profileImgUrl = awsS3Uploader.uploadImage(profileImg);
        MemberProfileImage memberProfileImage = new MemberProfileImage(profileImgUrl, true);
        memberProfileImageRepository.save(memberProfileImage);
        member.updateProfileImgUrl(memberProfileImage);
    }
}

package com.konkuk.Eodikase.service;

import com.konkuk.Eodikase.dto.request.member.MemberProfileUpdateRequest;
import com.konkuk.Eodikase.dto.request.member.MemberSignUpRequest;
import com.konkuk.Eodikase.dto.request.member.ResetPasswordRequest;
import com.konkuk.Eodikase.dto.request.member.OAuthMemberSignUpRequest;
import com.konkuk.Eodikase.dto.request.member.PasswordVerifyRequest;
import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.entity.MemberPlatform;
import com.konkuk.Eodikase.domain.member.entity.MemberStatus;
import com.konkuk.Eodikase.domain.member.entity.MemberProfileImage;
import com.konkuk.Eodikase.domain.member.repository.MemberProfileImageRepository;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.domain.member.service.MemberService;
import com.konkuk.Eodikase.dto.response.member.*;
import com.konkuk.Eodikase.exception.badrequest.*;
import com.konkuk.Eodikase.exception.notfound.NotFoundMemberException;
import com.konkuk.Eodikase.support.AwsS3Uploader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ServiceTest
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberProfileImageRepository memberProfileImageRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AwsS3Uploader awsS3Uploader;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        memberProfileImageRepository.deleteAll();
    }

    @Test
    @DisplayName("회원을 정상적으로 가입한다")
    void signUp() {
        String expected = "dlawotn3@naver.com";
        MemberSignUpRequest request = new MemberSignUpRequest(expected, "edks1234!", "감자");

        memberService.signUp(request);

        List<Member> actual = memberRepository.findAll();
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getEmail()).isEqualTo(expected);
    }

    @Test
    @DisplayName("이미 가입된 이메일이 존재하면 회원 가입 시에 예외를 반환한다")
    void signUpByDuplicateEmailMember() {
        String email = "dlawotn3@naver.com";
        memberRepository.save(new Member(email, "edks1234!", "감자", MemberPlatform.HOME,
                null));
        MemberSignUpRequest request = new MemberSignUpRequest(email, "edks1234!", "어디카세");

        assertThatThrownBy(() -> memberService.signUp(request))
                .isInstanceOf(DuplicateMemberException.class);
    }

    @Test
    @DisplayName("이미 가입된 닉네임이 존재하면 회원 가입 시에 예외를 반환한다")
    void signUpByDuplicateNicknameMember() {
        String nickname = "감자";
        memberRepository.save(new Member("dlawotn3@naver.com", "edks1234!", nickname,
                MemberPlatform.HOME, null));
        MemberSignUpRequest request = new MemberSignUpRequest("eodikase3@naver.com", "edks1234!",
                nickname);

        assertThatThrownBy(() -> memberService.signUp(request))
                .isInstanceOf(DuplicateNicknameException.class);
    }

    @Test
    @DisplayName("회원 비밀번호를 정상적으로 암호화한다")
    void encryptPassword() {
        String rawPassword = "1234";

        String encryptedPassword = passwordEncoder.encode(rawPassword);

        Boolean matches = passwordEncoder.matches(rawPassword, encryptedPassword);
        assertThat(matches).isTrue();
    }

    @ParameterizedTest
    @DisplayName("비밀번호가 8~15자가 아니면 예외를 반환한다")
    @ValueSource(strings = {"abcdef7", "abcdefgabcdefgabc21"})
    void passwordLengthValidation(String password) {
        assertThatThrownBy(() -> memberService.signUp(new MemberSignUpRequest("dlawotn3@naver.com", password,
                "감자")))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @ParameterizedTest
    @DisplayName("비밀번호가 영어, 숫자, 특수문자를 모두 포함하지 않으면 예외를 반환한다")
    @ValueSource(strings = {"abcdefgh", "12345678", "abc1234", "abc!!!!"})
    void passwordConfigureValidation(String password) {
        assertThatThrownBy(() -> memberService.signUp(new MemberSignUpRequest("dlawotn3@naver.com",
                password, "감자")))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    @DisplayName("이미 존재하는 이메일인 경우 True를 반환한다")
    void isDuplicateEmailReturnTrue(){
        String email = "dlawotn3@naver.com";
        MemberSignUpRequest request = new MemberSignUpRequest(email, "edks1234!", "감자");
        memberService.signUp(request);

        IsDuplicateEmailResponse response = memberService.isDuplicateEmail(email);

        assertThat(response.isResult()).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 이메일인 경우 False를 반환한다")
    void isDuplicateEmailReturnFalse(){
        String email = "dlawotn3@naver.com";

        IsDuplicateEmailResponse response = memberService.isDuplicateEmail(email);

        assertThat(response.isResult()).isFalse();
    }

    @Test
    @DisplayName("이미 존재하는 닉네임인 경우 True를 반환한다")
    void isDuplicateNicknameReturnTrue() {
        String nickname = "감자";
        MemberSignUpRequest request = new MemberSignUpRequest("dlawotn3@naver.com", "edks1234!",
                nickname);
        memberService.signUp(request);

        IsDuplicateNicknameResponse response = memberService.isDuplicateNickname(nickname);

        assertThat(response.isResult()).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 닉네임인 경우 False를 반환한다")
    void isDuplicateNicknameReturnFalse() {
        String nickname = "감자";

        IsDuplicateNicknameResponse response = memberService.isDuplicateNickname(nickname);

        assertThat(response.isResult()).isFalse();
    }

    @ParameterizedTest
    @DisplayName("닉네임이 2~15자가 아니면 예외를 반환한다")
    @ValueSource(strings = {"감", "감자포테이토토토토토토토토토토토토토예에에"})
    void nicknameLengthValidation(String nickname) {
        assertThatThrownBy(() -> memberService.signUp(new MemberSignUpRequest("dlawotn3@naver.com",
                "edks1234!", nickname)))
                .isInstanceOf(InvalidNicknameException.class);
    }

    @Test
    @DisplayName("비밀번호 변경 요청을 받을 경우, 새로운 비밀번호로 변경한다")
    void resetPassword() {
        String email = "dlawotn3@naver.com";
        String updatePassword = "newpwd1234!";
        Member member = memberRepository.save(new Member(email, "edks1234!", "감자",
                MemberPlatform.HOME, null));
        ResetPasswordRequest request = new ResetPasswordRequest(updatePassword);

        memberService.resetPassword(member.getId(), request);

        Member actual = memberRepository.findById(member.getId())
                .orElseThrow();
        boolean isPasswordCorrect = passwordEncoder.matches(updatePassword, actual.getPassword());
        assertThat(isPasswordCorrect).isTrue();
    }

    @Test
    @DisplayName("올바르지 않은 비밀번호로 비밀번호 찾기 요청을 받을 경우 예외를 반환한다")
    void findAndResetPasswordWhenInvalidPassword() {
        String email = "dlawotn3@naver.com";
        Member member = memberRepository.save(new Member(email, "edks1234!", "감자",
                MemberPlatform.HOME, null));
        ResetPasswordRequest request = new ResetPasswordRequest("123");

        assertThatThrownBy(() -> memberService.resetPassword(member.getId(), request))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    @DisplayName("OAuth 유저 로그인 후 회원가입 시 platform과 platformId 정보로 회원이 존재하지 않으면 예외를 반환한다")
    void signUpByOAuthMemberWhenInvalidPlatformInfo() {
        memberRepository.save(new Member("dlawotn3@naver.com", MemberPlatform.KAKAO, "1234321"));
        OAuthMemberSignUpRequest request = new OAuthMemberSignUpRequest(null, "감자",
                MemberPlatform.KAKAO.getValue(), "invalid");

        assertThatThrownBy(() -> memberService.signUpByOAuthMember(request))
                .isInstanceOf(NotFoundMemberException.class);
    }

    @Test
    @DisplayName("회원이 회원 정보를 수정하면 수정된 정보로 갱신된다")
    void updateProfileInfo() {
        String email = "dlawotn3@naver.com";
        String password = "edks1234!";
        String originalNickname = "감자";
        String newNickname = "돌이";
        String newIntro = "hi";
        Member member = new Member(email, passwordEncoder.encode(password), originalNickname, MemberPlatform.HOME,
                null);
        memberRepository.save(member);

        memberService.updateProfileInfo(member.getId(), new MemberProfileUpdateRequest(newNickname, newIntro));
        Member updatedMember = memberRepository.findById(member.getId())
                .orElseThrow();

        assertAll(
                () -> assertThat(updatedMember.getNickname()).isEqualTo(newNickname),
                () -> assertThat(updatedMember.getIntro()).isEqualTo(newIntro)
        );
    }

    @Test
    @DisplayName("회원이 잘못된 닉네임 형식으로 회원정보 수정을 시도하면 예외를 반환한다")
    void updateBadNicknameWithValidateException() {
        String email = "dlawotn3@naver.com";
        String password = "edks1234!";
        String originalNickname = "감자";
        String newNickname = "감";
        String newIntro = "hi";
        Member member = new Member(email, passwordEncoder.encode(password), originalNickname, MemberPlatform.HOME,
                null);
        memberRepository.save(member);

        MemberProfileUpdateRequest request = new MemberProfileUpdateRequest(newNickname, newIntro);
        assertThatThrownBy(() -> memberService.updateProfileInfo(member.getId(), request))
                .isInstanceOf(InvalidNicknameException.class);
    }

    @Test
    @DisplayName("회원이 중복된 닉네임으로 회원정보 수정을 시도하면 예외를 반환한다")
    void updateDuplicateNicknameWithValidateException() {
        String email = "dlawotn3@naver.com";
        String password = "edks1234!";
        String originalNickname = "감자";
        String newNickname = "돌이";
        String newIntro = "hi";
        Member member = memberRepository.save(new Member(email, password, originalNickname, MemberPlatform.HOME,
                null));
        memberRepository.save(new Member("dlawotn2@naver.com", "edks123!!!", "돌이",
                MemberPlatform.HOME, null));

        MemberProfileUpdateRequest request = new MemberProfileUpdateRequest(newNickname, newIntro);
        assertThatThrownBy(() -> memberService.updateProfileInfo(member.getId(), request))
                .isInstanceOf(DuplicateNicknameException.class);
    }

    @Test
    @DisplayName("회원이 옳은 비밀번호로 비밀번호 확인 인증을 성공한다")
    void verifyPasswordReturnTrue() {
        String email = "dlawotn3@naver.com";
        String password = "edks1234!";
        String nickname = "감자";
        Member member = new Member(email, passwordEncoder.encode(password), nickname, MemberPlatform.HOME,
                null);
        memberRepository.save(member);
        PasswordVerifyRequest request = new PasswordVerifyRequest(password);

        PasswordVerifyResponse actual = memberService.verifyPassword(member.getId(), request);

        assertThat(actual.getIsSuccess()).isTrue();
    }

    @Test
    @DisplayName("회원이 틀린 비밀번호로 비밀번호 확인 인증을 실패한다")
    void verifyPasswordReturnFalse() {
        String email = "dlawotn3@naver.com";
        String password = "edks1234!";
        String nickname = "감자";
        Member member = new Member(email, passwordEncoder.encode(password), nickname, MemberPlatform.HOME,
                null);
        memberRepository.save(member);
        PasswordVerifyRequest request = new PasswordVerifyRequest("wrong123!");

        PasswordVerifyResponse actual = memberService.verifyPassword(member.getId(), request);

        assertThat(actual.getIsSuccess()).isFalse();
    }

    @Test
    @DisplayName("프로필 수정 페이지에서 내 정보를 조회한다")
    void getUpdateProfileInfo() {
        String email = "dlawotn3@naver.com";
        String password = "edks1234!";
        String nickname = "감자";
        Member member = new Member(email, passwordEncoder.encode(password), nickname, MemberPlatform.HOME,
                null);
        memberRepository.save(member);

        GetUpdateProfileInfoResponse actual = memberService.getUpdateProfileInfo(member.getId());

        assertAll(
                () -> assertThat(actual.getEmail()).isEqualTo(email),
                () -> assertThat(actual.getNickname()).isEqualTo(nickname),
                () -> assertThat(actual.getIntro()).isEqualTo("")
        );
    }

    @Test
    @DisplayName("타회원의 프로필 정보를 조회한다")
    void getMemberProfileInfo() {
        String email = "dlawotn2@naver.com";
        String nickname = "니니";
        Member member1 = new Member("dlawotn3@naver.com", passwordEncoder.encode("edks1234!"),
                "감자", MemberPlatform.HOME, null);
        Member member2 = new Member(email, passwordEncoder.encode("edks1234!"),
                nickname, MemberPlatform.HOME, null);
        memberRepository.save(member1);
        memberRepository.save(member2);

        MemberPageResponse actual = memberService.findMemberInfo(member2.getId());

        assertAll(
                () -> assertThat(actual.getEmail()).isEqualTo(email),
                () -> assertThat(actual.getNickname()).isEqualTo(nickname),
                () -> assertThat(actual.getIntro()).isEqualTo("")
        );
    }

    @Test
    @DisplayName("회원을 정상적으로 탈퇴한다")
    void deleteMember() {
        String email = "dlawotn3@naver.com";
        String password = "edks1234!";
        String nickname = "감자";
        Member member = new Member(email, passwordEncoder.encode(password), nickname, MemberPlatform.HOME,
                null);
        memberRepository.save(member);

        memberService.delete(member.getId());

        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember.get().getStatus()).isEqualTo(MemberStatus.MEMBER_QUIT);
    }

    @Test
    @DisplayName("회원의 프로필 이미지를 변경하면 s3 서버와 연동하여 이미지를 업로드한다")
    void updateProfileImg() throws IOException {
        String expected = "test_img.jpg";
        String password = "edks1234!";
        Member member = new Member("dlawotn3@naver.com", passwordEncoder.encode(password), "감자", MemberPlatform.HOME,
                null);
        memberRepository.save(member);
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/images/" + expected);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test_img", expected, "jpg",
                fileInputStream);

        when(awsS3Uploader.uploadImage(mockMultipartFile)).thenReturn("test_img.jpg");
        memberService.updateProfileImage(member.getId(), mockMultipartFile);

        Member actual = memberRepository.findById(member.getId())
                .orElseThrow();
        assertAll(
                () -> Assertions.assertThat(actual.getImgUrl()).isEqualTo(expected),
                () -> Assertions.assertThat(actual.getMemberProfileImage().getIsUsed()).isTrue()
        );
    }

    @Test
    @DisplayName("회원이 프로필 이미지를 삭제하거나 null로 설정하면 프로필 이미지는 null로 설정된다")
    void updateProfileImgWithNull() {
        MemberProfileImage memberProfileImage = new MemberProfileImage("test_img.jpg");
        memberProfileImageRepository.save(memberProfileImage);
        String password = "edks1234!";
        Member member = new Member("dlawotn3@naver.com", passwordEncoder.encode(password), "감자",
                MemberPlatform.HOME, memberProfileImage);
        memberRepository.save(member);

        memberService.updateProfileImage(member.getId(), null);

        Member actual = memberRepository.findById(member.getId())
                .orElseThrow();
        assertAll(
                () -> Assertions.assertThat(actual.getImgUrl()).isNull(),
                () -> Assertions.assertThat(actual.getMemberProfileImage()).isNull()
        );
    }

    @Test
    @DisplayName("마이페이지로 내 정보를 조회한다")
    void findMyInfo() {
        String imgUrl = "test_img.jpg";
        String email = "dlawotn3@naver.com";
        String nickname = "지슈";
        MemberProfileImage memberProfileImage = new MemberProfileImage(imgUrl);
        memberProfileImageRepository.save(memberProfileImage);
        Member member = new Member(email, "jisu0708!", nickname, MemberPlatform.HOME,
                memberProfileImage);
        memberRepository.save(member);

        MyPageResponse actual = memberService.findMyInfo(member.getId());

        assertAll(
                () -> assertThat(actual.getEmail()).isEqualTo(email),
                () -> assertThat(actual.getImgUrl()).isEqualTo(imgUrl),
                () -> assertThat(actual.getNickname()).isEqualTo(nickname)
        );
    }
}

package com.konkuk.Eodikase.domain.member.controller;

import com.konkuk.Eodikase.domain.member.service.MemberService;
import com.konkuk.Eodikase.dto.request.*;
import com.konkuk.Eodikase.dto.response.*;
import com.konkuk.Eodikase.security.auth.LoginUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Tag(name = "Members", description = "회원")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "이메일 회원가입")
    @PostMapping
    public Response<Object> signUp(@RequestBody @Valid MemberSignUpRequest request) {
        MemberSignUpResponse response = memberService.signUp(request);
        return Response.ofSuccess("OK", response.getId());
    }

    @Operation(summary = "OAuth 회원가입")
    @PostMapping("/oauth")
    public Response<Object> signUp(@RequestBody @Valid OAuthMemberSignUpRequest request) {
        OAuthMemberSignUpResponse response = memberService.signUpByOAuthMember(request);
        return Response.ofSuccess("OK", response.getId());
    }

    @Operation(summary = "회원가입 이메일 중복체크")
    @GetMapping("/check-duplicate/email")
    public Response<Object> checkDuplicateEmail(@RequestParam String email) {
        IsDuplicateEmailResponse response = memberService.isDuplicateEmail(email);
        return Response.ofSuccess("OK", response.isResult());
    }

    @Operation(summary = "회원가입 닉네임 중복체크")
    @GetMapping("/check-duplicate/nickname")
    public Response<Object> checkDuplicateNickname(@RequestParam String nickname) {
        IsDuplicateNicknameResponse response = memberService.isDuplicateNickname(nickname);
        return Response.ofSuccess("OK", response.isResult());
    }

    @Operation(summary = "프로필 회원정보 수정")
    @SecurityRequirement(name = "JWT")
    @PutMapping(value = "/info")
    public Response<?> updateProfileInfo(
            @LoginUserId Long memberId,
            @RequestBody @Valid MemberProfileUpdateRequest request
    ) {
        memberService.updateProfileInfo(memberId, request);
        return Response.ofSuccess("OK", null);
    }

    @Operation(summary = "비밀번호 변경")
    @SecurityRequirement(name = "JWT")
    @PutMapping("/info/reset-password")
    public Response<?> resetPassword(
            @LoginUserId Long memberId,
            @RequestBody @Valid ResetPasswordRequest request
    ) {
        memberService.resetPassword(memberId, request);
        return Response.ofSuccess("OK", null);
    }

    @Operation(summary = "프로필 회원정보 수정 페이지 조회")
    @SecurityRequirement(name = "JWT")
    @GetMapping(value = "/info")
    public Response<?> getUpdateProfileInfo(
            @LoginUserId Long memberId
    ) {
        GetUpdateProfileInfoResponse response = memberService.getUpdateProfileInfo(memberId);
        return Response.ofSuccess("OK", response);
    }

    @Operation(summary = "비밀번호 확인 인증")
    @SecurityRequirement(name = "JWT")
    @PostMapping("/info/password")
    public Response<?> passwordVerify(
            @LoginUserId Long memberId,
            @RequestBody @Valid PasswordVerifyRequest request
    ) {
        PasswordVerifyResponse response = memberService.verifyPassword(memberId, request);
        return Response.ofSuccess("OK", response);
    }

    @Operation(summary = "마이페이지 조회")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/mypage")
    public Response<?> findMyInfo(@LoginUserId Long memberId) {
        MyPageResponse response = memberService.findMyInfo(memberId);
        return Response.ofSuccess("OK", response);
    }

    @Operation(summary = "마이페이지 - 프로필 이미지 수정")
    @SecurityRequirement(name = "JWT")
    @PutMapping(value = "/mypage/img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<?> updateProfileImage(
            @LoginUserId Long memberId,
            @RequestParam(value = "key", required = false) MultipartFile multipartFile
    ) {
        memberService.updateProfileImage(memberId, multipartFile);
        return Response.ofSuccess("OK", null);
    }

    @Operation(summary = "회원 탈퇴")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping
    public Response<?> delete(@LoginUserId Long memberId) {
        memberService.delete(memberId);
        return Response.ofSuccess("OK", null);
    }
}

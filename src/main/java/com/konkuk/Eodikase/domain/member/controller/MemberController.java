package com.konkuk.Eodikase.domain.member.controller;

import com.konkuk.Eodikase.domain.member.dto.request.MemberProfileUpdateRequest;
import com.konkuk.Eodikase.domain.member.dto.request.MemberSignUpRequest;
import com.konkuk.Eodikase.domain.member.dto.request.OAuthMemberSignUpRequest;
import com.konkuk.Eodikase.domain.member.dto.request.PasswordVerifyRequest;
import com.konkuk.Eodikase.domain.member.dto.response.*;
import com.konkuk.Eodikase.domain.member.service.MemberService;
import com.konkuk.Eodikase.security.auth.LoginUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MemberSignUpResponse> signUp(@RequestBody @Valid MemberSignUpRequest request) {
        MemberSignUpResponse response = memberService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "OAuth 회원가입")
    @PostMapping("/oauth")
    public ResponseEntity<OAuthMemberSignUpResponse> signUp(@RequestBody @Valid OAuthMemberSignUpRequest request) {
        OAuthMemberSignUpResponse response = memberService.signUpByOAuthMember(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원가입 이메일 중복체크")
    @GetMapping("/check-duplicate/email")
    public ResponseEntity<IsDuplicateEmailResponse> checkDuplicateEmail(@RequestParam String value) {
        IsDuplicateEmailResponse response = memberService.isDuplicateEmail(value);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원가입 닉네임 중복체크")
    @GetMapping("/check-duplicate/nickname")
    public ResponseEntity<IsDuplicateNicknameResponse> checkDuplicateNickname(@RequestParam String value) {
        IsDuplicateNicknameResponse response = memberService.isDuplicateNickname(value);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "프로필 회원정보 수정")
    @SecurityRequirement(name = "JWT")
    @PutMapping(value = "/info")
    public ResponseEntity<Void> updateProfileInfo(
            @LoginUserId Long memberId,
            @RequestBody @Valid MemberProfileUpdateRequest request
    ) {
        memberService.updateProfileInfo(memberId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "프로필 회원정보 수정 페이지 조회")
    @SecurityRequirement(name = "JWT")
    @GetMapping(value = "/info")
    public ResponseEntity<GetUpdateProfileInfoResponse> getUpdateProfileInfo(
            @LoginUserId Long memberId
    ) {
        GetUpdateProfileInfoResponse response = memberService.getUpdateProfileInfo(memberId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "비밀번호 확인 인증")
    @SecurityRequirement(name = "JWT")
    @PostMapping("/info/password")
    public ResponseEntity<PasswordVerifyResponse> passwordVerify(
            @LoginUserId Long memberId,
            @RequestBody @Valid PasswordVerifyRequest request
    ) {
        PasswordVerifyResponse response = memberService.verifyPassword(memberId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "마이페이지 - 프로필 이미지 수정")
    @SecurityRequirement(name = "JWT")
    @PutMapping(value = "/mypage/img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateProfileImage(
            @LoginUserId Long memberId,
            @RequestParam(value = "file", required = false) MultipartFile multipartFile
    ) {
        memberService.updateProfileImage(memberId, multipartFile);
        return ResponseEntity.ok().build();
    }
}

package com.konkuk.Eodikase.domain.member.controller;

import com.konkuk.Eodikase.domain.member.dto.request.MemberSignUpRequest;
import com.konkuk.Eodikase.domain.member.dto.response.IsDuplicateEmailResponse;
import com.konkuk.Eodikase.domain.member.dto.response.MemberSignUpResponse;
import com.konkuk.Eodikase.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Members", description = "회원")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @PostMapping
    public ResponseEntity<MemberSignUpResponse> signUp(@RequestBody @Valid MemberSignUpRequest request) {
        MemberSignUpResponse response = memberService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원가입 이메일 중복체크")
    @GetMapping("/check-duplicate/email")
    public ResponseEntity<IsDuplicateEmailResponse> checkDuplicateEmail(@RequestParam String value) {
        IsDuplicateEmailResponse response = memberService.isDuplicateEmail(value);
        return ResponseEntity.ok(response);
    }
}

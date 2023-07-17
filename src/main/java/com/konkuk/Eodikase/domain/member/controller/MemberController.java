package com.konkuk.Eodikase.domain.member.controller;

import com.konkuk.Eodikase.domain.member.dto.request.MemberSignUpRequest;
import com.konkuk.Eodikase.domain.member.dto.response.MemberSignUpResponse;
import com.konkuk.Eodikase.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberSignUpResponse> signUp(@RequestBody MemberSignUpRequest request) {
        MemberSignUpResponse response = memberService.signUp(request);
        return ResponseEntity.ok(response);
    }
}

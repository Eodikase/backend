package com.konkuk.Eodikase.domain.like.controller;

import com.konkuk.Eodikase.domain.like.service.LikeService;
import com.konkuk.Eodikase.dto.response.Response;
import com.konkuk.Eodikase.security.auth.LoginUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/likes")
public class LikeController {

    private final LikeService likeService;
    @Operation(summary = "코스 좋아요")
    @SecurityRequirement(name = "JWT")
    @PostMapping("/{course-id}")
    public Response like(
            @LoginUserId Long memberId,
            @PathVariable("course-id") Long courseId
    ){
        likeService.like(memberId, courseId);
        return Response.ofSuccess("OK",null);
    }
    @Operation(summary = "코스 좋아요 취소")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{course-id}")
    public Response cancelLike(
            @LoginUserId Long memberId,
            @PathVariable("course-id") Long courseId
    ){
        likeService.cancel(memberId, courseId);
        return Response.ofSuccess("OK",null);
    }

}

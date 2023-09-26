package com.konkuk.Eodikase.domain.bookmark.controller;

import com.konkuk.Eodikase.domain.bookmark.service.BookmarkService;
import com.konkuk.Eodikase.domain.like.service.LikeService;
import com.konkuk.Eodikase.dto.response.Response;
import com.konkuk.Eodikase.security.auth.LoginUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    @Operation(summary = "북마크 등록")
    @SecurityRequirement(name = "JWT")
    @PostMapping("/{course-id}")
    public Response insert(
            @LoginUserId Long memberId,
            @PathVariable("course-id") Long courseId
    ){
        bookmarkService.insert(memberId, courseId);
        return Response.ofSuccess("OK",null);

    }
    @Operation(summary = "북마크 취소")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{course-id}")
    public Response delete(
            @LoginUserId Long memberId,
            @PathVariable("course-id") Long courseId
    ){
        bookmarkService.delete(memberId, courseId);
        return Response.ofSuccess("OK",null);
    }

}

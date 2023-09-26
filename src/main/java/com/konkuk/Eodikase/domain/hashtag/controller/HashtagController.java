package com.konkuk.Eodikase.domain.hashtag.controller;

import com.konkuk.Eodikase.domain.hashtag.entity.HashTagName;
import com.konkuk.Eodikase.domain.hashtag.entity.Hashtag;
import com.konkuk.Eodikase.domain.hashtag.repository.HashtagRepository;
import com.konkuk.Eodikase.domain.hashtag.service.HashtagService;
import com.konkuk.Eodikase.dto.request.course.CoursePostRequest;
import com.konkuk.Eodikase.dto.response.Response;
import com.konkuk.Eodikase.dto.response.hashtag.HashtagResponse;
import com.konkuk.Eodikase.security.auth.LoginUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hashtags")
public class HashtagController {

    private final HashtagRepository hashtagRepository;

    @Operation(summary = "해시태그 조회")
    @GetMapping
    public Response getHashtags() {
        List<Hashtag> all = hashtagRepository.findAll();
        List<HashtagResponse> response = all.stream().map(HashtagResponse::new).collect(Collectors.toList());

        return Response.ofSuccess("OK", response);
    }

}

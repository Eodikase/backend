package com.konkuk.Eodikase.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchCourseDatasResponse {

    private List<SearchCourseDataResponse> content;

    private Boolean last;
}

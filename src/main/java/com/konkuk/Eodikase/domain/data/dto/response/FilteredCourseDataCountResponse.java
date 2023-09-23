package com.konkuk.Eodikase.domain.data.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FilteredCourseDataCountResponse {
    private int count;
}

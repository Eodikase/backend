package com.konkuk.Eodikase.domain.data.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class FilteredCourseDataCountRequest {
    private double lat;
    private double lng;
}

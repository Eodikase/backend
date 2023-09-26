package com.konkuk.Eodikase.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilteredCourseDataCountResponse {
    private int stage1;
    private int stage2;
    private int stage3;
    private int stage4;
}

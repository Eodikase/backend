package com.konkuk.Eodikase.domain.data.dto.response;

import lombok.Getter;

@Getter
public class FilteredCourseDataCountResponse {
    private int stage1;
    private int stage2;
    private int stage3;
    private int stage4;

    public FilteredCourseDataCountResponse(int stage1, int stage2, int stage3, int stage4) {
        this.stage1 = stage1;
        this.stage2 = stage2;
        this.stage3 = stage3;
        this.stage4 = stage4;
    }
}

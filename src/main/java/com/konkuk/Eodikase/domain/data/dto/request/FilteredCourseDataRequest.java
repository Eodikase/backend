package com.konkuk.Eodikase.domain.data.dto.request;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class FilteredCourseDataRequest {
    private double lat;
    private double lng;
}

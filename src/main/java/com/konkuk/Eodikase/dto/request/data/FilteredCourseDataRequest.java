package com.konkuk.Eodikase.dto.request.data;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class FilteredCourseDataRequest {
    private double lat;
    private double lng;
}

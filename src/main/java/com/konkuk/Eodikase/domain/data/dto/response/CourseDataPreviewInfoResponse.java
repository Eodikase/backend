package com.konkuk.Eodikase.domain.data.dto.response;

import lombok.Getter;

@Getter
public class CourseDataPreviewInfoResponse {
    private Long dataId;
    private String dataName;
    private String dataCategory;
    private String dataOperatingTime;
    private String dataLocation;
    private String dataPhoneNumber;
    private String dataImageUrl;
}

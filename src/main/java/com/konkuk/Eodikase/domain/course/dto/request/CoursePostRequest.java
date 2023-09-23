package com.konkuk.Eodikase.domain.course.dto.request;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class CoursePostRequest {

    //코스에 넣을 코스데이터 아이디
    private Long CourseDataid;

    //장소 (건성서 등)
    private String region;

    //코스에서 몇번째 장소인지
    private int order;

}

package com.konkuk.Eodikase.domain.course.entity;


import lombok.Getter;

@Getter
public enum CourseRegion {
    EM("EM"),//을지명동
    HI("HI"),//한남이태원
    HSE("HSE"),//홍대신촌이대
    KSS("KSS"),//건대성수서울숲
    NS("NS"),//논현신사
    SBG("SBG"),//서촌북촉광화문
    SH("SH");//신촌합정

    private String region;

    CourseRegion( String region ) {
        this.region = region;
    }
}

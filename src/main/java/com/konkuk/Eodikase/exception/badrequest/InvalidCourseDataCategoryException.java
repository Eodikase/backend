package com.konkuk.Eodikase.exception.badrequest;

public class InvalidCourseDataCategoryException extends BadRequestException {

    public InvalidCourseDataCategoryException() {
        super("코스 데이터 카테고리 명이 올바르지 않습니다.", 3001);
    }
}

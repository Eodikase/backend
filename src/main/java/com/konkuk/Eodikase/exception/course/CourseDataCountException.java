package com.konkuk.Eodikase.exception.course;

import com.konkuk.Eodikase.exception.badrequest.BadRequestException;

public class CourseDataCountException extends BadRequestException {

    public CourseDataCountException() {
        super("장소는 2개이상 11개이하 등록 가능합니다.",2000);
    }
}

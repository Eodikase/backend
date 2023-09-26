package com.konkuk.Eodikase.exception.course;

import com.konkuk.Eodikase.exception.badrequest.BadRequestException;

public class CourseNotAuthorizedException extends BadRequestException {

    public CourseNotAuthorizedException() {
        super("자신의 코스만 삭제가 가능합니다", 4001);
    }
}

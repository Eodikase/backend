package com.konkuk.Eodikase.exception.score;

import com.konkuk.Eodikase.exception.badrequest.BadRequestException;

public class NotMyCourseException extends BadRequestException {

    public NotMyCourseException() {
        super("자신의 코스가 아닙니다.", 4001);
    }

}

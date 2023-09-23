package com.konkuk.Eodikase.exception.notfound;

import lombok.Getter;

@Getter
public class NotFoundCourseException extends NotFoundException {

    public NotFoundCourseException() {
        super("존재하지 않는 코스입니다.", 1007);
    }
}

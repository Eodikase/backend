package com.konkuk.Eodikase.exception.notfound;

import lombok.Getter;

@Getter
public class NotFoundCourseDataException extends NotFoundException {

    public NotFoundCourseDataException() {
        super("존재하지 않는 코스 데이터입니다.", 3003);
    }
}

package com.konkuk.Eodikase.exception.badrequest;

public class InvalidDataOrderException extends BadRequestException {

    public InvalidDataOrderException() {
        super("유효하지 않은 데이터 순서입니다.", 3002);
    }
}

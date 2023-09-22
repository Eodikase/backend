package com.konkuk.Eodikase.exception.badrequest;

public class InvalidOrderException extends BadRequestException {

    public InvalidOrderException() {
        super("순서가 올바르지 않습니다.", 3002);
    }
}

package com.konkuk.Eodikase.exception.badrequest;

public class InvalidPasswordException extends BadRequestException {

    public InvalidPasswordException() {
        super("비밀번호 형식이 올바르지 않습니다.", 1002);
    }
}

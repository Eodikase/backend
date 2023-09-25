package com.konkuk.Eodikase.exception.badrequest;

public class InvalidIntroException extends BadRequestException {

    public InvalidIntroException() {
        super("자기소개 글자 수가 150자를 초과합니다.", 1015);
    }
}

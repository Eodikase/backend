package com.konkuk.Eodikase.exception.badrequest;

public class InvalidMemberPlatformException extends BadRequestException {

    public InvalidMemberPlatformException() {
        super("플랫폼 정보가 올바르지 않습니다.", 1012);
    }
}

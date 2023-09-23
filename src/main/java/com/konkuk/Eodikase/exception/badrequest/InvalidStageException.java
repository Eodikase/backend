package com.konkuk.Eodikase.exception.badrequest;

public class InvalidStageException extends BadRequestException {

    public InvalidStageException() {
        super("유효하지 않은 반경 stage입니다.", 3004);
    }
}

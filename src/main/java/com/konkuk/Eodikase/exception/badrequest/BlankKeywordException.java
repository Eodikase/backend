package com.konkuk.Eodikase.exception.badrequest;

public class BlankKeywordException extends BadRequestException {

    public BlankKeywordException() {
        super("키워드는 공백일 수 없습니다.", 3005);
    }
}

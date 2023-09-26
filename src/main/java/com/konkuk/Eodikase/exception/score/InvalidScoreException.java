package com.konkuk.Eodikase.exception.score;

import com.konkuk.Eodikase.exception.badrequest.BadRequestException;

public class InvalidScoreException extends BadRequestException {

    public InvalidScoreException() {
        super("별점은 1점과 5점 사이입니다", 8000);
    }
}

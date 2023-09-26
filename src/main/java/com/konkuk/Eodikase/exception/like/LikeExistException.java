package com.konkuk.Eodikase.exception.like;

import com.konkuk.Eodikase.exception.badrequest.BadRequestException;

public class LikeExistException extends BadRequestException {

    public LikeExistException() {
        super("이미 좋아요 표시한 코스입니다.", 7000);
    }
}

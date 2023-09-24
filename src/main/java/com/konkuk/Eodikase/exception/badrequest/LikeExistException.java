package com.konkuk.Eodikase.exception.badrequest;

public class LikeExistException extends BadRequestException{

    public LikeExistException() {
        super("이미 좋아요 표시한 코스입니다.", 3005);
    }
}

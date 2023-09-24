package com.konkuk.Eodikase.exception.badrequest;

public class LikeNotExistException extends BadRequestException{

    public LikeNotExistException() {
        super("좋아요를 취소할 수 없습니다.", 3007);
    }
}

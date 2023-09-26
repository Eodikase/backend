package com.konkuk.Eodikase.exception.like;

import com.konkuk.Eodikase.exception.badrequest.BadRequestException;

public class LikeNotExistException extends BadRequestException {

    public LikeNotExistException() {
        super("좋아요를 취소할 수 없습니다.", 7001);
    }
}

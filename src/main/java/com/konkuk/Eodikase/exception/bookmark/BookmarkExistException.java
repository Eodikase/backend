package com.konkuk.Eodikase.exception.bookmark;

import com.konkuk.Eodikase.exception.badrequest.BadRequestException;

public class BookmarkExistException extends BadRequestException {

    public BookmarkExistException() {
        super("이미 북마크한 코스입니다.", 5000);
    }
}

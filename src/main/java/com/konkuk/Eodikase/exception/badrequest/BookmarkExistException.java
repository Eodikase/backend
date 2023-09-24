package com.konkuk.Eodikase.exception.badrequest;

public class BookmarkExistException extends BadRequestException{

    public BookmarkExistException() {
        super("이미 북마크한 코스입니다.", 3007);
    }
}

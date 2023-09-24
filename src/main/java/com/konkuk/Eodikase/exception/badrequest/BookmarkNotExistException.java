package com.konkuk.Eodikase.exception.badrequest;

public class BookmarkNotExistException extends BadRequestException{

    public BookmarkNotExistException() {
        super("북마크를 취소할 수 없습니다.", 3008);
    }
}

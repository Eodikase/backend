package com.konkuk.Eodikase.exception.bookmark;

import com.konkuk.Eodikase.exception.badrequest.BadRequestException;

public class BookmarkNotExistException extends BadRequestException {

    public BookmarkNotExistException() {
        super("북마크를 취소할 수 없습니다.", 5002);
    }
}

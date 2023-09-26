package com.konkuk.Eodikase.exception.bookmark;

import com.konkuk.Eodikase.exception.badrequest.BadRequestException;

public class BookmarkMyException extends BadRequestException {

    public BookmarkMyException() {
        super("자신의 코스는 북마크 할 수 없습니다.", 3008);
    }
}

package com.konkuk.Eodikase.exception.hashtag;

import com.konkuk.Eodikase.exception.notfound.NotFoundException;
import lombok.Getter;

@Getter
public class NotFoundHashtagException extends NotFoundException {

    public NotFoundHashtagException() {
        super("존재하지 않는 해시태그입니다.", 1007);
    }
}

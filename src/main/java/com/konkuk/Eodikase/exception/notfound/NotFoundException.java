package com.konkuk.Eodikase.exception.notfound;

import com.konkuk.Eodikase.exception.EodikaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends EodikaseException {

    public NotFoundException(String message, int code) {
            super(HttpStatus.NOT_FOUND, message, code);
    }
}

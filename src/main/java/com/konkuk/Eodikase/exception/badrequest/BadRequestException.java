package com.konkuk.Eodikase.exception.badrequest;

import com.konkuk.Eodikase.exception.EodikaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends EodikaseException {

    public BadRequestException(String message, int code) {
        super(HttpStatus.BAD_REQUEST, message, code);
    }
}

package com.konkuk.Eodikase.exception.unauthorized;

import com.konkuk.Eodikase.exception.EodikaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends EodikaseException {

    public UnauthorizedException(String message, int code) {
        super(HttpStatus.UNAUTHORIZED, message, code);
    }
}

package com.konkuk.Eodikase.exception.badrequest;

public class InvalidRegionException extends BadRequestException {

    public InvalidRegionException() {
        super("지역 명이 올바르지 않습니다.", 3000);
    }
}

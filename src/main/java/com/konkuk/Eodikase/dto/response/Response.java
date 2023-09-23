package com.konkuk.Eodikase.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<D> {

    private String success;
    private String message;
    private D data;

    public static <D> Response<D> ofSuccess(String message, D data) {
        return new Response<>("200", message, data);
    }
}

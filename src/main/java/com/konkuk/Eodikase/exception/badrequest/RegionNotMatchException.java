package com.konkuk.Eodikase.exception.badrequest;

public class RegionNotMatchException extends BadRequestException{

    public RegionNotMatchException() {
        super("코스데이터의 지역과 코스지역이 맞지 않습니다.", 3005);
    }
}

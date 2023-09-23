package com.konkuk.Eodikase;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        System.out.println(org.hibernate.Version.getVersionString());
        return "yml 관련 서브모듈 브랜치 업데이트 ";

    }
}

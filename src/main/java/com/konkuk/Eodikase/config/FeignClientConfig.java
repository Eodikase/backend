package com.konkuk.Eodikase.config;

import com.konkuk.Eodikase.EodikaseApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = EodikaseApplication.class)
public class FeignClientConfig {
}

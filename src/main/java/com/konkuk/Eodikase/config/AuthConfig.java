package com.konkuk.Eodikase.config;

import com.konkuk.Eodikase.security.auth.AuthenticationPrincipalArgumentResolver;
import com.konkuk.Eodikase.security.auth.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/v1/members/**")
                .addPathPatterns("/v1/courseDatas/**")
                .excludePathPatterns("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs", "/error")
                .excludePathPatterns("/v1/members", "/v1/members/oauth", "/v1/members/check-duplicate/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}

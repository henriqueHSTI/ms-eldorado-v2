package com.eldorado.commons.interception.header;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final HeaderInterceptor headerInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerInterceptor);
    }

}

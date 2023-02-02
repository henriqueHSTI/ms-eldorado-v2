package com.eldorado.commons.interception.header;

import com.eldorado.commons.security.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Order(HeaderInterceptor.ORDER)
@Slf4j
@RequiredArgsConstructor
public class HeaderInterceptor implements HandlerInterceptor {

    public static final int ORDER = Ordered.HIGHEST_PRECEDENCE + 3;

    private final AuthUtils authUtils;

    @Override
    @SneakyThrows
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Request Validation to endpoint {} ", request.getRequestURI());
        if (authUtils.AUTHORIZED_PATHS.contains(request.getRequestURI())) {
            log.info("Endpoint {} don't need authentication ", request.getRequestURI());
            return true;
        } else return authUtils.validateJwtToken(authorization, response);

    }

}

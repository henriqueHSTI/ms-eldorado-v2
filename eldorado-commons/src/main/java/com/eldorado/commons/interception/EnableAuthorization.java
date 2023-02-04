package com.eldorado.commons.interception;


import com.eldorado.commons.exceptions.EldoradoExceptionHandler;
import com.eldorado.commons.interception.header.HeaderInterceptor;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ApiImplicitParams({
        @ApiImplicitParam(
                name = HttpHeaders.AUTHORIZATION,
                value = HttpHeaders.AUTHORIZATION,
                paramType = "header",
                dataTypeClass = String.class,
                required = true
        )
})
@Import({EnableAuthorization.Authorization.class,
        HeaderInterceptor.class,
        EldoradoExceptionHandler.class})
public @interface EnableAuthorization {

    @Slf4j
    class Authorization implements BeanFactoryPostProcessor {
        @Override
        public void postProcessBeanFactory(final @NonNull ConfigurableListableBeanFactory configurableListableBeanFactory)
                throws BeansException {
            log.debug("Enable Authorization module...");
        }
    }
}

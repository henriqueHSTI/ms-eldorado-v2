package com.eldorado.commons.configuration;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({EnableMapper.Mapper.class, MapperConfigurations.class})
public @interface EnableMapper {

    @Slf4j
    class Mapper implements BeanFactoryPostProcessor {
        @Override
        public void postProcessBeanFactory(final @NonNull ConfigurableListableBeanFactory configurableListableBeanFactory)
                throws BeansException {
            log.debug("Enable Mapper module...");
        }
    }
}

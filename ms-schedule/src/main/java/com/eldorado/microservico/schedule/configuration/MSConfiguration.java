package com.eldorado.microservico.schedule.configuration;

import com.eldorado.commons.security.AuthUtils;
import com.eldorado.microservico.schedule.domain.model.AppointmentEntity;
import com.eldorado.microservico.schedule.dto.AppointmentDto;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MSConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(AppointmentDto.class, AppointmentEntity.class)
                .addMapping(AppointmentDto::getAppointmentDate, AppointmentEntity::setWorkDate);
        return modelMapper;
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("MS-Schedule")
                .description("MS - Schedule")
                .version("1.0.0"));
    }

    @Bean
    public AuthUtils authUtils() {
        return new AuthUtils();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

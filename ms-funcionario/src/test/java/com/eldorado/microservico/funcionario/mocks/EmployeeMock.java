package com.eldorado.microservico.funcionario.mocks;

import com.eldorado.microservico.funcionario.domain.EmployeeEntity;
import com.eldorado.microservico.funcionario.dto.EmployeeDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmployeeMock {

    public static EmployeeDto createValidUserDto(String username, String email) {
        return EmployeeDto.builder().id(UUID.randomUUID()).name(username).email(email).build();
    }

    public static EmployeeDto createValidUserDtoMatheus() {
        return createValidUserDto("Matheus Wiener", "matheuswiener9@gmail.com");
    }

    public static EmployeeEntity createValidEmployeeEntity(String name, String email, String password, String createBy) {
        return EmployeeEntity.builder()
                .id(UUID.randomUUID())
                .createAt(LocalDateTime.now())
                .name(name)
                .email(email)
                .password(password)
                .createBy(createBy)
                .build();
    }

    public static EmployeeEntity createValidUserEntityMatheus() {
        return createValidEmployeeEntity("Matheus Wiener", "matheuswiener9@gmail.com", "123456", "Matheus");
    }
}

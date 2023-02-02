package com.eldorado.microservico.funcionario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {
    private UUID id;
    private String name;
    private String email;

}

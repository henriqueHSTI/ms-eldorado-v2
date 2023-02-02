package com.eldorado.microservico.funcionario.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document("Employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {

    @Id
    private UUID id;
    private String name;
    @Indexed(unique = true)
    private String email;
    private String password;
    private LocalDateTime createAt;
    private String createBy;
}

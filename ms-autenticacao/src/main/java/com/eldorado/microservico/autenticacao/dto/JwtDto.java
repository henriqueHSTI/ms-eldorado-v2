package com.eldorado.microservico.autenticacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtDto implements Serializable {
    private String token;
    private String userName;
    private String email;

}

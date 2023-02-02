package com.eldorado.microservico.usuario.mocks;

import com.eldorado.commons.dto.UserLoginDto;
import com.eldorado.microservico.usuario.dto.UserDto;

import java.time.LocalDate;

public class UserMock {

    public static UserDto createValidUserDto() {
        return UserDto.builder().name("Bruno Henrique").document("1112222019").userName("bruno.henrique@hsti.eng.br").birthDate(LocalDate.now()).build();
    }

    public static UserDto createInValidUserDto() {
        return new UserDto();
    }


    public static UserLoginDto createValidUserLoginDto() {
        return UserLoginDto.builder().userName("henrique@hsti.eng.br").password("123456").build();
    }

}

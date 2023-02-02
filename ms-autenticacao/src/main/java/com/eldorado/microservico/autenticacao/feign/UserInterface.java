package com.eldorado.microservico.autenticacao.feign;


import com.eldorado.commons.dto.UserDto;
import com.eldorado.commons.dto.UserLoginDto;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-usuario")
public interface UserInterface {

    @PostMapping(value = "/user/login")
    ResponseEntity<UserDto> getLogin(@RequestBody UserLoginDto userLoginDto);

}

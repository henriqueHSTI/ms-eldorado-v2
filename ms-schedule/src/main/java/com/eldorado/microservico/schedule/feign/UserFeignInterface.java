package com.eldorado.microservico.schedule.feign;


import com.eldorado.commons.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuario")
public interface UserFeignInterface {

    @GetMapping(value = "/user/{document}")
    UserDto getLogin(@PathVariable String document);
}

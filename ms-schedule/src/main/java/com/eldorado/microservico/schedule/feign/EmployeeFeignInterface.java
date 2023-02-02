package com.eldorado.microservico.schedule.feign;


import com.eldorado.commons.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-funcionario")
public interface EmployeeFeignInterface {

    @GetMapping(value = "/employee/{employeeId}")
    EmployeeDto getEmployee(@PathVariable UUID employeeId);

}

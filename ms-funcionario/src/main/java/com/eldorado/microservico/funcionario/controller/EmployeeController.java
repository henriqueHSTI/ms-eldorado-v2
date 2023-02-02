package com.eldorado.microservico.funcionario.controller;

import com.eldorado.microservico.funcionario.dto.EmployeeDto;
import com.eldorado.microservico.funcionario.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("Saving employee {}", employeeDto);

        return ResponseEntity.ok(employeeService.createEmployee(employeeDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> retrieveAllEmployees() {
        log.info("Retrieving all employees");

        return ResponseEntity.ok(employeeService.retriveAllEmployees());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable UUID employeeId) {
        log.info("Retrieving all employees");
        return ResponseEntity.ok(employeeService.findById(employeeId));
    }


}

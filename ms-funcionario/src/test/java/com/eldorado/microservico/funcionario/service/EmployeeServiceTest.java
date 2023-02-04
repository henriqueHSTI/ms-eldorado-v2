package com.eldorado.microservico.funcionario.service;

import com.eldorado.microservico.funcionario.dto.EmployeeDto;
import com.eldorado.microservico.funcionario.domain.EmployeeEntity;
import com.eldorado.microservico.funcionario.mocks.EmployeeMock;
import com.eldorado.microservico.funcionario.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;


    @Test
    void createEmployeeWithSucefully() {
        //arrange
        EmployeeDto employee = EmployeeMock.createValidUserDtoMatheus();
        Mockito.when(modelMapper.map(employee, EmployeeEntity.class))
                .thenReturn(EmployeeEntity.builder()
                        .id(employee.getId())
                        .name(employee.getName())
                        .email(employee.getEmail())
                        .password("123456")
                        .createAt(LocalDateTime.now())
                        .createBy("Matheus")
                        .build());

        //action
        var result = employeeService.createEmployee(employee);

        verify(employeeRepository, times(1)).save(any());

        Assertions.assertNotNull(result);
    }

    @Test
    void findEmployeeByIdWithSucefully() {
        //arrange
        var employee = EmployeeMock.createValidUserDtoMatheus();
        var employeeEntity = Optional.of(new EmployeeEntity());
        Mockito.when(employeeRepository.findById(any())).thenReturn(employeeEntity);
        Mockito.when(modelMapper.map(employeeEntity, EmployeeDto.class)).thenReturn(employee);

        //action
        var result = employeeService.findById(employee.getId());
        //assetion

        Assertions.assertNotNull(result);
        verify(employeeRepository, times(1)).findById(employee.getId());

    }

    @Test
    void findAllEmployeesWithSucefully() {
        //arrange
        List<EmployeeEntity> employeeList = new ArrayList<>();
        var employee = EmployeeMock.createValidUserDtoMatheus();
        var employeeEntity = EmployeeMock.createValidUserEntityMatheus();
        employeeList.add(employeeEntity);

        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        Mockito.when(modelMapper.map(employeeEntity, EmployeeDto.class)).thenReturn(employee);

        var result = employeeService.retriveAllEmployees();

        Assertions.assertNotNull(result);
        verify(employeeRepository, times(1)).findAll();

    }
}
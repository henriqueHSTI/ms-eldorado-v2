package com.eldorado.microservico.funcionario.service;

import com.eldorado.microservico.funcionario.domain.EmployeeEntity;
import com.eldorado.microservico.funcionario.dto.EmployeeDto;
import com.eldorado.microservico.funcionario.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        var entity = modelMapper.map(employeeDto, EmployeeEntity.class);

        entity.setId(Optional.ofNullable(employeeDto.getId()).orElse(UUID.randomUUID()));

        entity.setCreateAt(LocalDateTime.now());
        entity.setCreateBy("JOSE");

        var entitySave = employeeRepository.save(entity);

        modelMapper.map(entitySave, employeeDto);

        log.info("Created employee id {}", employeeDto.getId());

        return employeeDto;
    }

    public List<EmployeeDto> retriveAllEmployees() {

        List<EmployeeEntity> entities = employeeRepository.findAll();
        log.info("Found {} employees", entities.size());

        return entities.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .toList();

    }

    public EmployeeDto findById(UUID employeerId) {
        return modelMapper.map(employeeRepository.findById(employeerId), EmployeeDto.class);
    }
}

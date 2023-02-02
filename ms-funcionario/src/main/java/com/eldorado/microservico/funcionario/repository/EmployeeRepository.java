package com.eldorado.microservico.funcionario.repository;

import com.eldorado.microservico.funcionario.domain.EmployeeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EmployeeRepository extends MongoRepository<EmployeeEntity, UUID> {
}

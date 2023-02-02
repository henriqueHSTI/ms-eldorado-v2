package com.eldorado.microservico.usuario.domain.repository;

import com.eldorado.microservico.usuario.domain.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUserName(String useName);
}

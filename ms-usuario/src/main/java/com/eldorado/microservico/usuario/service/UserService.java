package com.eldorado.microservico.usuario.service;


import com.eldorado.commons.dto.UserLoginDto;
import com.eldorado.commons.exceptions.BadRequestException;
import com.eldorado.commons.exceptions.NotFoundException;
import com.eldorado.microservico.usuario.domain.model.UserEntity;
import com.eldorado.microservico.usuario.domain.repository.UserRepository;
import com.eldorado.microservico.usuario.dto.MessageDto;
import com.eldorado.microservico.usuario.dto.UserDto;
import com.eldorado.microservico.usuario.publisher.UserPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private static final String MESSAGE = "Cadastro realizado\nUsuario: %s\nSenha: %s";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final String SUBJECT = "NÃO RESPONDA";

    private final ModelMapper modelMapper;

    private final ObjectMapper objectMapper;

    private final UserRepository userRepository;

    private final UserPublisher userPublisher;
    private final PasswordEncoder passwordEncoder;


    public UserDto createUser(@Valid @NonNull UserDto userDto) {
        log.info("Is a valid email {}", vefifyEmail(userDto.getUserName()));
        var userEntity = modelMapper.map(userDto, UserEntity.class);
        var password = passwordGeneretor();
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity = userRepository.save(userEntity);
        log.info("User Saved with sucefull {}", userEntity);
        sendMessage(userDto, password);
        return userDto;
    }

    @SneakyThrows
    private void sendMessage(UserDto userDto, String password) {
        var message = MessageDto.builder().to(userDto.getUserName()).message(String.format(MESSAGE, userDto.getUserName(), password)).subject(SUBJECT).build();
        userPublisher.sendToQueue(objectMapper.writeValueAsString(message));
        log.info("Message to queue {}", message);
    }

    private String passwordGeneretor() {
        RandomStringUtils.randomAlphabetic(10);
        return Base64.encodeBase64String(RandomStringUtils.randomAlphanumeric(10).getBytes());
    }

    @SneakyThrows
    public UserDto login(UserLoginDto userLoginDto) {
        log.info("Retrieve information to login {}", userLoginDto.getUserName());
        var user = userRepository.findByUserName(userLoginDto.getUserName())
                .orElseThrow(() -> new NotFoundException("Invalid Access"));
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto findById(@NotNull String document) {
        log.info("Retrieve user to Id  {}", document);
        var user = userRepository.findById(document).orElseThrow(() -> new NotFoundException(String.format("User {%s} Not Found", document)));

        return modelMapper.map(user, UserDto.class);
    }

    private boolean vefifyEmail(String username) {

        if (VALID_EMAIL_ADDRESS_REGEX.matcher(username).find())
            return true;

        log.error("Invalid email {} ", username);
        throw new BadRequestException(String.format("Invalid email %s", username));

    }
}

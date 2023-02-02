package com.eldorado.microservico.usuario.service;

import com.eldorado.commons.exceptions.BadRequestException;
import com.eldorado.commons.exceptions.NotFoundException;
import com.eldorado.microservico.usuario.domain.model.UserEntity;
import com.eldorado.microservico.usuario.domain.repository.UserRepository;
import com.eldorado.microservico.usuario.dto.UserDto;
import com.eldorado.microservico.usuario.mocks.UserMock;
import com.eldorado.microservico.usuario.publisher.UserPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserPublisher userPublisher;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void createUserWithSucefully() {
        //arrange
        var user = UserMock.createValidUserDto();
        Mockito.when(modelMapper.map(user, UserEntity.class)).thenReturn(UserEntity.builder().name(user.getName()).birthDate(user.getBirthDate()).userName(user.getUserName()).build());
        Mockito.when(passwordEncoder.encode(any())).thenReturn("123456789");

        //action
        var result = userService.createUser(user);

        //assetion

        verify(userRepository, times(1)).save(any());
        verify(userPublisher).sendToQueue(any());

        Assertions.assertNotNull(result);
    }

    @Test
    void verifyEmailWithFailed() {
        var user = UserMock.createInValidUserDto();
        user.setUserName("bh");
        assertThrows(BadRequestException.class, () -> userService.createUser(user));
    }

    @Test
    void loginWithSucefully() {

        //arrange
        var userLogin = UserMock.createValidUserLoginDto();
        var user = UserMock.createValidUserDto();
        var userEntity = new UserEntity();
        Mockito.when(userRepository.findByUserName(any())).thenReturn(Optional.of(userEntity));
        Mockito.when(modelMapper.map(userEntity, UserDto.class)).thenReturn(user);

        //action
        var result = userService.login(userLogin);

        //assertion

        assertNotNull(result);
        verify(userRepository, times(1)).findByUserName(any());
    }

    @Test
    void loginWithFailed() {

        //arrange
        var userLogin = UserMock.createValidUserLoginDto();
        Mockito.when(userRepository.findByUserName(any())).thenThrow(NotFoundException.class);

        //action
        assertThrows(NotFoundException.class, () -> userService.login(userLogin));

        //assertion
        verify(userRepository, times(1)).findByUserName(any());
        verifyNoInteractions(modelMapper);
    }

    @Test
    void findByIdWithSucefully() {
        //arrange
        var user = UserMock.createValidUserDto();
        String document = "123456";
        var userEntity = Optional.of(new UserEntity());
        Mockito.when(userRepository.findById(any())).thenReturn(userEntity);
        Mockito.when(modelMapper.map(userEntity, UserDto.class)).thenReturn(user);

        //action
        var result = userService.findById(document);
        //assetion

        Assertions.assertNotNull(result);
        verify(userRepository, times(1)).findById(eq(document));

    }

    @Test
    void findByIdWithFaild() {
        //arrange
        String document = "123456";
        Mockito.when(userRepository.findById(any())).thenThrow(NotFoundException.class);

        //action
        //assetion
        assertThrows(NotFoundException.class, () -> userService.findById(document));

    }
}
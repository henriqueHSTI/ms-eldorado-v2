package com.eldorado.microservico.autenticacao.service;

import com.eldorado.commons.dto.UserLoginDto;
import com.eldorado.microservico.autenticacao.dto.JwtDto;
import com.eldorado.microservico.autenticacao.feign.UserInterface;
import com.eldorado.microservico.autenticacao.security.AuthUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AuthUtils authUtils;

    private final ObjectMapper objectMapper;

    @Autowired
    private UserInterface userInterface;





    public JwtDto doAuthentication(UserLoginDto userLoginDto, Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var jwt = authUtils.generationJwtToken(authentication);

        User user = (User) authentication.getPrincipal();

        return JwtDto.builder()
                .token(jwt)
                .userName(user.getUsername().replaceAll("@.*", ""))
                .email(userLoginDto.getUserName())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {


        var userDto = userInterface.getLogin(UserLoginDto.builder().userName(userName).build()).getBody();

        return new User(userDto.getUserName(),
                userDto.getPassword(), Collections.emptyList());
    }

}

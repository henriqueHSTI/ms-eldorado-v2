package com.eldorado.microservico.autenticacao.controller;

import com.eldorado.commons.dto.UserLoginDto;
import com.eldorado.microservico.autenticacao.dto.JwtDto;
import com.eldorado.microservico.autenticacao.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/eldorado/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticatorController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping
    public ResponseEntity<JwtDto> authenticator(@RequestBody UserLoginDto userLoginDto) {

        log.info("Authenticatio user {} ", userLoginDto.getUserName());

        var authenticator = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getUserName(),
                        userLoginDto.getPassword()));

        return ResponseEntity.ok(userDetailsService.doAuthentication(userLoginDto, authenticator));
    }


}

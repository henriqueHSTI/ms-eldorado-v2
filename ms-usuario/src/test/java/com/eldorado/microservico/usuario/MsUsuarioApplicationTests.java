package com.eldorado.microservico.usuario;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MsUsuarioApplicationTests {

    @Test
    void contextLoads() {

        log.info(Base64.encodeBase64String(RandomStringUtils.randomAlphanumeric(10).getBytes()));

    }

}

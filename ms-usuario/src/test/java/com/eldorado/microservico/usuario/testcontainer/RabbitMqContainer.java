package com.eldorado.microservico.usuario.testcontainer;

import jakarta.validation.constraints.NotNull;
import org.testcontainers.containers.GenericContainer;

public class RabbitMqContainer extends GenericContainer<RabbitMqContainer> {

    public static final int RABBIT_PORT = 5672;

    public static final String DEFAULT_IMAGE = "rabbitmq:management";
    public static final String PASSWORD = "admin";
    public static final String USER_NAME = "admin";

    public RabbitMqContainer() {
        this(DEFAULT_IMAGE);
    }

    public RabbitMqContainer(@NotNull String image) {
        super(image);
        addExposedPort(RABBIT_PORT);
    }

    public Integer getPort() {
        return getMappedPort(RABBIT_PORT);
    }


}

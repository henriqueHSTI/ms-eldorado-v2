package com.eldorado.microservico.funcionario.testcontainer;

import jakarta.validation.constraints.NotNull;
import org.testcontainers.containers.GenericContainer;

public class MongoDbContainer extends GenericContainer<MongoDbContainer> {

    public static final int MONGODB_PORT = 27017;

    public static final String DEFAULT_IMAGE = "mongo:latest";

    public MongoDbContainer() {
        this(DEFAULT_IMAGE);
    }

    public MongoDbContainer(@NotNull String image) {
        super(image);
        addExposedPort(MONGODB_PORT);
    }

    public Integer getPort() {
        return getMappedPort(MONGODB_PORT);
    }
}

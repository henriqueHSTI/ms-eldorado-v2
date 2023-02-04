package com.eldorado.microservico.usuario.publisher;

import com.eldorado.microservico.usuario.testcontainer.MongoDbContainer;
import com.eldorado.microservico.usuario.testcontainer.RabbitMqContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = {UserPublisherIT.RabbitmqInitializer.class, UserPublisherIT.MongoDbInitializer.class})
class UserPublisherIT {


    private static RabbitMqContainer rabbitMqContainer;

    private static MongoDbContainer mongoDbContainer;
    @Autowired
    private UserPublisher userPublisher;

    @BeforeAll
    public static void startRabbit() {
        rabbitMqContainer = new RabbitMqContainer();
        rabbitMqContainer.start();

        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
    }

    @Test
    void testContainer() {
        userPublisher.sendToQueue("One Message");
    }

    public static class RabbitmqInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    String.format("spring.rabbitmq.host=%s", rabbitMqContainer.getHost(),
                            String.format("spring.rabbitmq.port=%s", rabbitMqContainer.getPort())),
                    String.format("spring.rabbitmq.username=%s", rabbitMqContainer.USER_NAME),
                    String.format("spring.rabbitmq.password=%s", rabbitMqContainer.PASSWORD)
            );
            values.applyTo(configurableApplicationContext);
        }
    }

    public static class MongoDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    String.format("spring.data.mongodb.host=%s", mongoDbContainer.getHost(),
                            String.format("spring.data.mongodb.port=%s", mongoDbContainer.getPort()))
            );
            values.applyTo(configurableApplicationContext);
        }
    }

}
package com.eldorado.microservico.usuario.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("user")
@Builder
public class UserEntity {

    @Id
    private String document;
    @NonNull
    private String name;
    private char gender;
    @NonNull
    private LocalDate birthDate;
    @NonNull
    @Indexed(unique = true)
    private String userName;
    private String password;

    private AddressEntity addressEntity;

    @Override
    public String toString() {
        return "UserEntity{" +
                "document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", email='" + userName + '\'' +
                '}';
    }
}

package com.eldorado.microservico.schedule.domain.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private String name;
    @NonNull
    private String document;
}

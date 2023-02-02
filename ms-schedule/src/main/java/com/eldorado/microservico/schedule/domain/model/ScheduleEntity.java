package com.eldorado.microservico.schedule.domain.model;


import com.eldorado.microservico.schedule.dto.WeekEnum;
import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Document("schedule")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {
    @Id
    private UUID id;
    @Indexed
    @NonNull
    private UUID employeeId;
    @NonNull
    private WeekEnum dayOfWeek;
    @NonNull
    private List<String> workTimes;
    @NonNull
    private LocalDate workDate;
    private String createdBy;
    private LocalDateTime createdAt;

}

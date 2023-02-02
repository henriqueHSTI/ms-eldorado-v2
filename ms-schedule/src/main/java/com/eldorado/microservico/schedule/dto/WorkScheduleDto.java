package com.eldorado.microservico.schedule.dto;

import com.eldorado.microservico.schedule.helper.ScheduleHelper;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class WorkScheduleDto {

    @NotNull
    private UUID employeeId;

    private WeekEnum dayOfWeek;

    private List<String> workTimes = ScheduleHelper.WORK_TIMES;

    private LocalDate workDate;

}

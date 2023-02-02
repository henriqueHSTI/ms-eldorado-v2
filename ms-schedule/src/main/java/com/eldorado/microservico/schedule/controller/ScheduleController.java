package com.eldorado.microservico.schedule.controller;

import com.eldorado.microservico.schedule.dto.AppointmentDto;
import com.eldorado.microservico.schedule.dto.AvailableTimeDto;
import com.eldorado.microservico.schedule.dto.WorkScheduleDto;
import com.eldorado.microservico.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/work-schedule")
    public void saveWorkSchedule(@RequestBody List<WorkScheduleDto> workScheduleDto) {
        log.info("Save work Schedule to employee");
        scheduleService.saveWorkSchedule(workScheduleDto);
    }

    @GetMapping("/work-schedule/{employeeId}/{workDate}")
    public ResponseEntity<AvailableTimeDto> retrieveWorkSchedule(@PathVariable UUID employeeId, @PathVariable LocalDate workDate) {
        log.info("Save work Schedule to employee");
        return ResponseEntity.ok(scheduleService.getListAvailableTimes(employeeId, workDate));
    }

    @PostMapping("/appointment")
    public ResponseEntity<AppointmentDto> saveAppoitment(@RequestBody @Valid AppointmentDto appointmentDto) {
        log.info("Save a appoitment");
        return ResponseEntity.ok(scheduleService.saveAppointment(appointmentDto));
    }
}

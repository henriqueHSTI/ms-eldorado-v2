package com.eldorado.microservico.schedule.service;

import com.eldorado.microservico.schedule.domain.model.AppointmentEntity;
import com.eldorado.microservico.schedule.domain.model.EmployeeEntity;
import com.eldorado.microservico.schedule.domain.model.ScheduleEntity;
import com.eldorado.microservico.schedule.domain.model.UserEntity;
import com.eldorado.microservico.schedule.domain.repository.AppointmentRepository;
import com.eldorado.microservico.schedule.domain.repository.ScheduleRepository;
import com.eldorado.microservico.schedule.dto.AppointmentDto;
import com.eldorado.microservico.schedule.dto.AvailableTimeDto;
import com.eldorado.microservico.schedule.dto.WorkScheduleDto;
import com.eldorado.microservico.schedule.feign.EmployeeFeignInterface;
import com.eldorado.microservico.schedule.feign.UserFeignInterface;
import io.jsonwebtoken.lang.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final AppointmentRepository appointmentRepository;

    private final EmployeeFeignInterface employeeFeignInterface;

    private final UserFeignInterface userFeignInterface;

    private final ModelMapper modelMapper;



    public void saveWorkSchedule(List<WorkScheduleDto> workScheduleDto) {
        List<ScheduleEntity> scheduleEntities = workScheduleDto.stream().map(work -> {
            var entity = modelMapper.map(work, ScheduleEntity.class);
            entity.setId(UUID.randomUUID());
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy("JOSE");
            return entity;
        }).toList();
        scheduleRepository.saveAll(scheduleEntities);
        log.info("Saved list of work ");
    }

    public AvailableTimeDto getListAvailableTimes(UUID employeeId, LocalDate localDate) {
        log.info("Retrived List available Times");
        var schedule = scheduleRepository.findByEmployeeIdAndWorkDate(employeeId, localDate);
        var appointmentList = appointmentRepository.findByEmployeeIdAndWorkDate(employeeId, localDate);

        return AvailableTimeDto.builder().availableTimes(schedule.getWorkTimes().stream().filter(time -> appointmentList.stream().noneMatch(appointment -> Objects.nullSafeEquals(appointment.getWorkTime(), time))).toList()).build();

    }

    public AppointmentDto saveAppointment(AppointmentDto appointmentDto) {

        var employee = employeeFeignInterface.getEmployee(appointmentDto.getEmployeeId());
        var user = userFeignInterface.getLogin(appointmentDto.getUserDocument());

        var entity = modelMapper.map(appointmentDto, AppointmentEntity.class);
        BeanUtils.copyProperties(appointmentDto, entity);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy("SYSTEM");
        entity.setId(UUID.randomUUID());
        entity.setUser(modelMapper.map(user, UserEntity.class));
        entity.setEmployee(modelMapper.map(employee, EmployeeEntity.class));
        var entitySave = appointmentRepository.save(entity);
        log.info("Saved a appointment to {}", appointmentDto.getUserDocument());
        BeanUtils.copyProperties(entitySave, appointmentDto);
        return appointmentDto;
    }
}

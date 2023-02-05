package com.eldorado.microservico.schedule.service;

import com.eldorado.commons.dto.EmployeeDto;
import com.eldorado.commons.dto.UserDto;
import com.eldorado.microservico.schedule.domain.model.AppointmentEntity;
import com.eldorado.microservico.schedule.domain.model.EmployeeEntity;
import com.eldorado.microservico.schedule.domain.model.UserEntity;
import com.eldorado.microservico.schedule.domain.repository.AppointmentRepository;
import com.eldorado.microservico.schedule.domain.repository.ScheduleRepository;
import com.eldorado.microservico.schedule.dto.AppointmentDto;
import com.eldorado.microservico.schedule.feign.EmployeeFeignInterface;
import com.eldorado.microservico.schedule.feign.UserFeignInterface;
import com.eldorado.microservico.schedule.helper.ScheduleHelper;
import com.eldorado.microservico.schedule.mocks.ScheduleMocks;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class ScheduleServiceTest {

    @InjectMocks
    ScheduleService service;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    AppointmentRepository appointmentRepository;

    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @Mock
    EmployeeFeignInterface employeeFeignInterface;

    @Mock
    UserFeignInterface userFeignInterface;

    @Test
    @DisplayName("Should save a schedule successfully")
    void saveWorkSchedule() {
        // arrange
        var scheduleMocker = new ScheduleMocks(this.modelMapper);
        var listDto = scheduleMocker.getListSheduleDtoMock();
        var listEntity = service.getScheduleEntities(listDto);
        lenient().when(this.scheduleRepository.saveAll(listEntity)).thenReturn(listEntity);
        // action
        this.service.saveWorkSchedule(listDto);
        // assertion
        verify(scheduleRepository, times(1)).saveAll(any());
    }
    @ParameterizedTest
    @DisplayName("Should return available time, less the selected one.")
    @ValueSource(strings = {"08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00", "17:00"})
    void getListAvailableTimes(String hour) {

        //arrange
        var scheduleMocker = new ScheduleMocks(this.modelMapper);
        var employeeId = UUID.randomUUID();
        var today = LocalDate.now();
        var user = UserEntity.builder().name("xiblau").document("123456").build();
        var employee = EmployeeEntity.builder().id(employeeId).name("xablau").email("xablau@gmail.com").build();
        var scheduleEntity = scheduleMocker.getScheduleEntityMock(employeeId);
        var appointments = Arrays.asList(scheduleMocker.getAppointmentEntity(employee, user, hour));

        when(scheduleRepository.findByEmployeeIdAndWorkDate(employee.getId(), today)).thenReturn(scheduleEntity);
        when(appointmentRepository.findByEmployeeIdAndWorkDate(employee.getId(), today)).thenReturn(appointments);
        //action
        var times = this.service.getListAvailableTimes(employeeId, today);
        //assertion
        verify(scheduleRepository, times(1)).findByEmployeeIdAndWorkDate(employee.getId(), today);
        verify(appointmentRepository, times(1)).findByEmployeeIdAndWorkDate(employee.getId(), today);
        assertTrue(!times.getAvailableTimes().isEmpty());
        assertFalse(times.getAvailableTimes().contains(hour));
    }

    @Test
    @DisplayName("Should return empty available time.")
    void getNoListAvailableTime() {
        //arrange
        var scheduleMocker = new ScheduleMocks(this.modelMapper);
        var employeeId = UUID.randomUUID();
        var today = LocalDate.now();
        var user = UserEntity.builder().name("xiblau").document("123456").build();
        var employee = EmployeeEntity.builder().id(employeeId).name("xablau").email("xablau@gmail.com").build();
        var scheduleEntity = scheduleMocker.getScheduleEntityMock(employeeId);
        var appointments = new ArrayList<AppointmentEntity>();
        ScheduleHelper.WORK_TIMES.forEach(time -> appointments.add(scheduleMocker.getAppointmentEntity(employee, user, time)));

        when(scheduleRepository.findByEmployeeIdAndWorkDate(employee.getId(), today)).thenReturn(scheduleEntity);
        when(appointmentRepository.findByEmployeeIdAndWorkDate(employee.getId(), today)).thenReturn(appointments);
        //action
        var times = this.service.getListAvailableTimes(employeeId, today);
        //assertion
        verify(scheduleRepository, times(1)).findByEmployeeIdAndWorkDate(employee.getId(), today);
        verify(appointmentRepository, times(1)).findByEmployeeIdAndWorkDate(employee.getId(), today);
        assertTrue(times.getAvailableTimes().isEmpty());
    }

    @Test
    @DisplayName("Should send appointment to save, and return it's self")
    void saveAppointment() {
        // arrange
        var today = LocalDate.now();
        var workTime = ScheduleHelper.WORK_TIMES.get(2);
        var employeeId = UUID.fromString("80889cd8-4bda-4b9e-af3f-ed65e253a238");
        var user = UserEntity.builder().name("xiblau").document("123456").build();
        var employee = EmployeeEntity.builder().id(employeeId).name("xablau").email("xablau@gmail.com").build();

        var userDto = this.modelMapper.map(user, UserDto.class);
        var employeeDto = this.modelMapper.map(employee, EmployeeDto.class);
        var appointment = AppointmentEntity.builder()
                .id(UUID.randomUUID())
                .employee(employee)
                .dayOfWeek(today.getDayOfWeek())
                .workDate(today)
                .user(user)
                .workTime(workTime).build();
        var appointmentDto = this.modelMapper.map(appointment, AppointmentDto.class);

        when(service.getEmployeeDto(appointmentDto)).thenReturn(employeeDto);
        when(service.getUserDto(appointmentDto)).thenReturn(userDto);

        // action
        var savedAppointment = this.service.saveAppointment(appointmentDto);

        // assertion
        assertNotNull(savedAppointment);
        assertEquals(savedAppointment.getEmployeeId(), appointment.getEmployee().getId());
    }
}
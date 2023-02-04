package com.eldorado.microservico.schedule.mocks;

import com.eldorado.microservico.schedule.domain.model.AppointmentEntity;
import com.eldorado.microservico.schedule.domain.model.EmployeeEntity;
import com.eldorado.microservico.schedule.domain.model.ScheduleEntity;
import com.eldorado.microservico.schedule.domain.model.UserEntity;
import com.eldorado.microservico.schedule.dto.WorkScheduleDto;
import com.eldorado.microservico.schedule.helper.ScheduleHelper;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScheduleMocks {

    private ModelMapper modelMapper;
    public ScheduleMocks (ModelMapper mapper){
        this.modelMapper = mapper;
    }
    public static List<WorkScheduleDto> getListSheduleDtoMock(){
        List<WorkScheduleDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            var schedule = new WorkScheduleDto();
            schedule.setEmployeeId(UUID.randomUUID());
            schedule.setDayOfWeek(LocalDate.now().getDayOfWeek());
            schedule.setWorkDate(LocalDate.now());
            list.add(schedule);
        }
        return list;
    }

    public List<ScheduleEntity> getListSheduleEntityMock(){
        var schedules = getListSheduleDtoMock();
        List<ScheduleEntity> scheduleEntities = schedules.stream().map(
                work -> mapDtoToEntity(work)
        ).toList();
        return scheduleEntities;
    }

    public ScheduleEntity mapDtoToEntity(WorkScheduleDto data){
        var entity = modelMapper.map(data, ScheduleEntity.class);
        entity.setId(UUID.randomUUID());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy("JOSE");
        return entity;
    }

    public ScheduleEntity getScheduleEntityMock(UUID emploeeId){
        var entity = new ScheduleEntity();
        entity.setEmployeeId(emploeeId);
        entity.setId(UUID.randomUUID());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy("JOSE");
        entity.setDayOfWeek(LocalDate.now().getDayOfWeek());
        entity.setWorkDate(LocalDate.now());
        entity.setWorkTimes(ScheduleHelper.WORK_TIMES);
        return entity;
    }

    public AppointmentEntity getAppointmentEntity(EmployeeEntity employee, UserEntity user, String hour){
        var entity = new AppointmentEntity();
        entity.setId(UUID.randomUUID());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy("JOSE");
        entity.setDayOfWeek(LocalDate.now().getDayOfWeek());
        entity.setWorkDate(LocalDate.now());
        entity.setEmployee(employee);
        entity.setUser(user);
        entity.setWorkTime(hour);
        return entity;
    }

}

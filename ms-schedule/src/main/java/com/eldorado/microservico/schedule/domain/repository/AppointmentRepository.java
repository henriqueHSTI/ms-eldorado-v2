package com.eldorado.microservico.schedule.domain.repository;

import com.eldorado.microservico.schedule.domain.model.AppointmentEntity;
import com.eldorado.microservico.schedule.domain.model.ScheduleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends MongoRepository<AppointmentEntity, UUID> {


    List<AppointmentEntity> findByEmployeeIdAndWorkDate(UUID employeeId, LocalDate workDate);

}

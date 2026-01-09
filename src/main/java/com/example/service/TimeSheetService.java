package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.dto.ApiResponse;
import com.example.dto.TimeSheetDto;
import com.example.entity.EmployeeEntity;
import com.example.entity.EmployeeStatus;
import com.example.entity.TimeLogEntity;
import com.example.entity.TimeLogStatus;
import com.example.entity.TimeSheetEntity;
import com.example.entity.TimeSheetStatus;
import com.example.repository.EmployeeRepository;
import com.example.repository.TimeLogRepository;
import com.example.repository.TimeSheetRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TimeSheetService {

    private TimeSheetRepository timesheetRepo;
    private TimeLogRepository timeLogRepo;
    private EmployeeRepository employeeRepo;
    private ModelMapper modelMapper;
    public TimeSheetService(
            TimeSheetRepository timesheetRepo,
            TimeLogRepository timeLogRepo,
            EmployeeRepository employeeRepo,
            ModelMapper modelMapper) {
        this.timesheetRepo = timesheetRepo;
        this.timeLogRepo = timeLogRepo;
        this.employeeRepo = employeeRepo;
        this.modelMapper=modelMapper;
    }
    @Transactional
    public ApiResponse<TimeSheetDto> create(TimeSheetDto timeSheetDto){
    	LocalDate start = LocalDate.now().withDayOfMonth(1);
    	LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
    	Optional<EmployeeEntity> status = employeeRepo.findByIdAndStatus(timeSheetDto.getEmployeeId(),EmployeeStatus.ACTIVE);
    	if(status.isEmpty()) {return new ApiResponse(false,"Employee not found or inactive",null);}
    	List<TimeLogEntity> list = timeLogRepo.findByEmployeeId_IdAndStatusAndWorkDateBetween(timeSheetDto.getEmployeeId(),TimeLogStatus.PENDING,start,end);
    	if(list.isEmpty()) {return new ApiResponse(false,"No log  hours available to submit",null);}
        BigDecimal totalHours = list.stream()
                .map(TimeLogEntity::getHours)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    	TimeSheetEntity map = modelMapper.map(timeSheetDto,TimeSheetEntity.class);
    	map.setEmployee(status.get());
    	map.setTotalHours(totalHours);
    	//map.setStatus(TimeSheetStatus.SUBMITTED);
    	TimeSheetEntity save = timesheetRepo.save(map);
    	list.forEach(timeLog->timeLog.setStatus(TimeLogStatus.SUBMITTED));
    	timeLogRepo.saveAll(list);
 
    	return new ApiResponse(true,"success",save.toDto());
    }
    
    
}

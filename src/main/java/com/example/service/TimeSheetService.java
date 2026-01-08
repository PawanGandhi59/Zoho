package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.EmployeeEntity;
import com.example.entity.TimeLogEntity;
import com.example.entity.TimeLogStatus;
import com.example.entity.TimeSheetEntity;
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

    public TimeSheetService(
            TimeSheetRepository timesheetRepo,
            TimeLogRepository timeLogRepo,
            EmployeeRepository employeeRepo) {
        this.timesheetRepo = timesheetRepo;
        this.timeLogRepo = timeLogRepo;
        this.employeeRepo = employeeRepo;
    }

    
}

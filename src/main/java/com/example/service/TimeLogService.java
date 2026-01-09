package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.example.dto.ApiResponse;
import com.example.dto.TimeLogDto;
import com.example.dto.TimeSheetDto;
import com.example.entity.EmployeeEntity;
import com.example.entity.EmployeeStatus;
import com.example.entity.JobEntity;
import com.example.entity.ProjectAssignmentEntity;
import com.example.entity.ProjectAssignmentId;
import com.example.entity.ProjectEntity;
import com.example.entity.TimeLogEntity;
import com.example.entity.TimeLogStatus;
import com.example.repository.EmployeeRepository;
import com.example.repository.JobRepository;
import com.example.repository.ProjectAssignmentRepository;
import com.example.repository.ProjectRepository;
import com.example.repository.TimeLogRepository;

@Service
public class TimeLogService {
	private TimeLogRepository timeLogRepository;
	private ModelMapper modelMapper;
	private EmployeeRepository employeeRepository;
	private ProjectRepository projectRepository;
	private ProjectAssignmentRepository projectAssignmentRepository;
	private JobRepository jobRepository;
	@Autowired
	public TimeLogService(TimeLogRepository timeLogRepository,ModelMapper modelMapper,EmployeeRepository employeeRepository,
			ProjectRepository projectRepository,ProjectAssignmentRepository projectAssignmentRepository,JobRepository jobRepository) {
		this.employeeRepository=employeeRepository;
		this.modelMapper=modelMapper;
		this.projectAssignmentRepository=projectAssignmentRepository;
		this.projectRepository=projectRepository;
		this.timeLogRepository=timeLogRepository;
		this.jobRepository=jobRepository;
	}
	
	public ApiResponse<TimeLogDto> create(TimeLogDto timeLogDto){
		Optional<EmployeeEntity> employeeStatus = employeeRepository.findByIdAndStatus(timeLogDto.getEmployeeId(),EmployeeStatus.ACTIVE);
		if(employeeStatus.isEmpty()) {return new ApiResponse(false,"Employee not found or inactive",null);}
		Optional<ProjectEntity> projectStatus = projectRepository.findByIdAndEndDate(timeLogDto.getProjectID(), null);
		if(projectStatus.isEmpty()) {return new ApiResponse(false,"project not found or inactive",null);}
		Optional<JobEntity> jobStatus = jobRepository.findById(timeLogDto.getJobId());
		if(jobStatus.isEmpty()) {return new ApiResponse(false,"Job not found",null);}
		if(!jobStatus.get().getProject().getId().equals(timeLogDto.getProjectID())) {return new ApiResponse(false,"Job does not belong to project",null);}
		Optional<ProjectAssignmentEntity> assignmentStatus = projectAssignmentRepository.findById(new ProjectAssignmentId(timeLogDto.getProjectID(),timeLogDto.getEmployeeId())); 
		if(assignmentStatus.isEmpty()) {return new ApiResponse(false,"Employee was not assigned this project",null);}
		BigDecimal logged = timeLogRepository.totalHoursForDay(
	                timeLogDto.getEmployeeId(), timeLogDto.getWorkDate());

	        if (logged.add(timeLogDto.getHours()).compareTo(new BigDecimal("24")) > 0) {
	           return new ApiResponse(false,"Cannot log more than 24 hours",null);
	        }
	        TimeLogEntity map = modelMapper.map(timeLogDto,TimeLogEntity.class);
	        map.setEmployeeId(employeeStatus.get());
	        map.setJobId(jobStatus.get());
	        map.setProjectId(projectStatus.get());
	        TimeLogEntity save = timeLogRepository.save(map);
	        return new ApiResponse(true,"success",save.toDto());
	}
	
    public List<TimeLogDto> getMonthlyLogs(Long empId, LocalDate monthStart) {

        LocalDate start = monthStart.withDayOfMonth(1);
        LocalDate end = monthStart.withDayOfMonth(monthStart.lengthOfMonth());

        return timeLogRepository
                .findByEmployeeId_IdAndWorkDateBetween(empId, start,end)
                .stream()
                .map(tle->tle.toDto())
                .toList();
    }
    
    public ApiResponse<TimeLogDto> edit(TimeLogDto timeLogDto){
    	Optional<TimeLogEntity> status = timeLogRepository.findByIdAndStatus(timeLogDto.getId(),TimeLogStatus.PENDING);
    	if(status.isEmpty()) {return new ApiResponse(false,"Permission denied,already submitted",null);}
    	Optional<ProjectEntity> projectStatus = projectRepository.findByIdAndEndDate(timeLogDto.getProjectID(), null);
		if(projectStatus.isEmpty()) {return new ApiResponse(false,"project not found or inactive",null);}
		Optional<JobEntity> jobStatus = jobRepository.findById(timeLogDto.getJobId());
		if(jobStatus.isEmpty()) {return new ApiResponse(false,"Job not found",null);}
		if(!jobStatus.get().getProject().getId().equals(timeLogDto.getProjectID())) {return new ApiResponse(false,"Job does not belong to project",null);}
		Optional<ProjectAssignmentEntity> assignmentStatus = projectAssignmentRepository.findById(new ProjectAssignmentId(timeLogDto.getProjectID(),timeLogDto.getEmployeeId())); 
		if(assignmentStatus.isEmpty()) {return new ApiResponse(false,"Employee was not assigned this project",null);}
    	TimeLogEntity timeLogEntity = status.get();
    	BigDecimal logged = timeLogRepository.totalHoursForDay(
                timeLogEntity.getEmployeeId().getId(), timeLogDto.getWorkDate(),timeLogDto.getId());
    	 if (logged.add(timeLogDto.getHours()).compareTo(new BigDecimal("24")) > 0) {
	           return new ApiResponse(false,"Cannot log more than 24 hours",null);
	        }
    	timeLogEntity.setBillableStatus(timeLogDto.getBillableStatus());
    	if(timeLogDto.getDescription()!=null) {
    	timeLogEntity.setDescription(timeLogDto.getDescription());}
    	timeLogEntity.setHours(timeLogDto.getHours());
    	timeLogEntity.setJobId(jobStatus.get());
    	timeLogEntity.setProjectId(projectStatus.get());
    	timeLogEntity.setWorkDate(timeLogDto.getWorkDate());
    	timeLogEntity.setWorkItem(timeLogDto.getWorkItem());
    	TimeLogEntity save = timeLogRepository.save(timeLogEntity);
    	return new ApiResponse(true,"success",save.toDto());
    }
    
    public ApiResponse<String> delete(Long id){
    	Optional<TimeLogEntity> status = timeLogRepository.findByIdAndStatus(id,TimeLogStatus.PENDING);
    	if(status.isEmpty()) {return new ApiResponse(false,"Cannot delete already submitted or not  found",null);}
    	timeLogRepository.deleteById(id);
    	return new ApiResponse(true,"Deleted successfully",null);
    }
}

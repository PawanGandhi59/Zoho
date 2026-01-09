package com.example.service;

import java.math.BigDecimal;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.ApiResponse;
import com.example.dto.LeaveBalanceDto;
import com.example.dto.LeaveRequestDto;
import com.example.dto.LeaveTypeDto;
import com.example.entity.EmployeeEntity;
import com.example.entity.EmployeeStatus;
import com.example.entity.LeaveBalanceEntity;
import com.example.entity.LeaveRequestEntity;
import com.example.entity.LeaveRequestStatus;
import com.example.entity.LeaveTypeEntity;
import com.example.entity.OrganizationEntity;
import com.example.entity.TeamEntity;
import com.example.entity.TeamStatus;
import com.example.repository.EmployeeRepository;
import com.example.repository.LeaveBalanceRepository;
import com.example.repository.LeaveRequestRepository;
import com.example.repository.LeaveTypeRepository;
import com.example.repository.OrganizationRepository;

@Service
public class LeaveService {
	private LeaveBalanceRepository leaveBalanceRepository;
	private LeaveRequestRepository leaveRequestRepository;
	private LeaveTypeRepository leaveTypeRepository;
	private EmployeeRepository employeeRepository;
	private OrganizationRepository organizationRepository;
	private ModelMapper modelMapper;
	@Autowired
	public LeaveService(LeaveBalanceRepository leaveBalanceRepository, LeaveRequestRepository leaveRequestRepository,
			LeaveTypeRepository leaveTypeRepository, EmployeeRepository employeeRepository,
			OrganizationRepository organizationRepository, ModelMapper modelMapper) {
	
		this.leaveBalanceRepository = leaveBalanceRepository;
		this.leaveRequestRepository = leaveRequestRepository;
		this.leaveTypeRepository = leaveTypeRepository;
		this.employeeRepository = employeeRepository;
		this.organizationRepository = organizationRepository;
		this.modelMapper = modelMapper;
	}
	
	public ApiResponse<LeaveTypeDto> createLeaveType(LeaveTypeDto leaveTypeDto){
		Optional<OrganizationEntity> organizationStatus = organizationRepository.findByIdAndStatus(leaveTypeDto.getOrganizationId(),"ACTIVE");
		if(organizationStatus.isEmpty()) {return new ApiResponse(false,"Organization not found or inactive",null);}
		boolean exists = leaveTypeRepository.existsByOrganizationId_IdAndName(leaveTypeDto.getOrganizationId(),leaveTypeDto.getName());
		if(exists) {return new ApiResponse(false,"Duplicate entry,record already exists",null);}
		LeaveTypeEntity map = modelMapper.map(leaveTypeDto,LeaveTypeEntity.class);
		map.setOrganizationId(organizationStatus.get());
		LeaveTypeEntity save = leaveTypeRepository.save(map);
		return new ApiResponse(true,"success",save.toDto());
	}
	
	public ApiResponse<LeaveBalanceDto> addLeaveBalance(LeaveBalanceDto leaveBalanceDto){
		Optional<EmployeeEntity> employee = employeeRepository.findByIdAndStatus(leaveBalanceDto.getEmployeeId(),EmployeeStatus.ACTIVE);
		if(employee.isEmpty()) {return new ApiResponse(false,"Employee not found or inactive",null);}
		Optional<LeaveTypeEntity> leaveType = leaveTypeRepository.findById(leaveBalanceDto.getLeaveTypeId());
		if(leaveType.isEmpty()) {return new ApiResponse(false,"No such  leave type found",null);}
		boolean exists = leaveBalanceRepository.existsByEmployee_IdAndLeaveType_IdAndYear(leaveBalanceDto.getEmployeeId(), leaveBalanceDto.getLeaveTypeId(),leaveBalanceDto.getYear());
		if(exists) {return new ApiResponse(false,"Duplicate entry,record already exists",null);}
		LeaveBalanceEntity map = modelMapper.map(leaveBalanceDto,LeaveBalanceEntity.class);
		map.setEmployee(employee.get());
		map.setLeaveType(leaveType.get());
		map.setAvailableDays(leaveType.get().getMaxDaysPerYear());
		LeaveBalanceEntity save = leaveBalanceRepository.save(map);
		return new ApiResponse(true,"success",save.toDto());
	}
	@Transactional
	public ApiResponse<LeaveRequestDto> requestLeave(LeaveRequestDto leaveRequestDto){
		boolean exists = leaveRequestRepository.existsByEmployee_IdAndLeaveTypeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(leaveRequestDto.getEmployeeId(),
																					 leaveRequestDto.getLeaveType(),
																					 leaveRequestDto.getEndDate(),
																					 leaveRequestDto.getStartDate()
																					
																					 );
		if(exists) {return new ApiResponse(false,"Duplicate entry,record already exists",null);}
		Optional<EmployeeEntity> status = employeeRepository.findByIdAndStatus(leaveRequestDto.getEmployeeId(),EmployeeStatus.ACTIVE);
		if(status.isEmpty()) {return new ApiResponse(false,"Employee not found or inactive",null);}
		if(leaveRequestDto.getStartDate().isAfter(leaveRequestDto.getEndDate())) {return new ApiResponse(false,"End date cannot be before start date",null);}
		Optional<LeaveBalanceEntity> optional = leaveBalanceRepository.findByEmployee_IdAndLeaveType_IdAndYear(leaveRequestDto.getEmployeeId(),leaveRequestDto.getLeaveType(),Year.now());
		if(optional.isEmpty()) {return new ApiResponse(false,"No such leave type found",null);}
		Long between = ChronoUnit.DAYS.between(leaveRequestDto.getStartDate(),leaveRequestDto.getEndDate());
		BigDecimal valueOf = BigDecimal.valueOf(between);
		if(valueOf.compareTo(optional.get().getAvailableDays())>0 ) {return new ApiResponse(false,"Not sufficient leave balance",null);}
		LeaveRequestEntity map = modelMapper.map(leaveRequestDto,LeaveRequestEntity.class);
		map.setEmployee(status.get());
		LeaveRequestEntity save = leaveRequestRepository.save(map);
		LeaveBalanceEntity entity = optional.get();
		entity.setAvailableDays(entity.getAvailableDays().subtract(valueOf));
		leaveBalanceRepository.save(entity);
		return new ApiResponse(true,"success",save.toDto());
	}
	
	public ApiResponse<LeaveRequestDto> approveRequest(Long requestId,Long approverID){
		Optional<LeaveRequestEntity> id = leaveRequestRepository.findByIdAndStatus(requestId,LeaveRequestStatus.APPLIED);
		if(id.isEmpty()) {return new ApiResponse(false,"No request found",null);}
		Optional<EmployeeEntity> status = employeeRepository.findByIdAndStatus(approverID,EmployeeStatus.ACTIVE);
		if(status.isEmpty()) {return new ApiResponse(false,"Approver not found or inactive",null);}
		Optional<OrganizationEntity> byIdAndStatus = organizationRepository.findByIdAndStatus(status.get().getOrganizationId().getId(),"ACTIVE");
		if(byIdAndStatus.isEmpty()) {return new ApiResponse(false,"Organization not found or inactive",null);}
		if(status.get().getDesignation().equalsIgnoreCase("hr") || status.get().getDesignation().equalsIgnoreCase("ceo")) 
	    {
			LeaveRequestEntity entity = id.get();
			entity.setStatus(LeaveRequestStatus.APPROVED);
			LeaveRequestEntity save = leaveRequestRepository.save(entity);
			return new ApiResponse(true,"success",save.toDto());
	    }
	    else {
		    	return new ApiResponse(false,"Unauthorized user",null);
		     }
	}
	
	public ApiResponse<LeaveRequestDto> rejectRequest(Long requestId,Long approverID){
		Optional<LeaveRequestEntity> id = leaveRequestRepository.findByIdAndStatus(requestId,LeaveRequestStatus.APPLIED);
		if(id.isEmpty()) {return new ApiResponse(false,"No request found",null);}
		Optional<EmployeeEntity> status = employeeRepository.findByIdAndStatus(approverID,EmployeeStatus.ACTIVE);
		if(status.isEmpty()) {return new ApiResponse(false,"Approver not found or inactive",null);}
		Optional<OrganizationEntity> byIdAndStatus = organizationRepository.findByIdAndStatus(status.get().getOrganizationId().getId(),"ACTIVE");
		if(byIdAndStatus.isEmpty()) {return new ApiResponse(false,"Organization not found or inactive",null);}
		if(status.get().getDesignation().equalsIgnoreCase("hr") || status.get().getDesignation().equalsIgnoreCase("ceo")) 
	    {
			LeaveRequestEntity entity = id.get();
			entity.setStatus(LeaveRequestStatus.REJECTED);
			int year = entity.getStartDate().getYear();
			Optional<LeaveBalanceEntity> optional = leaveBalanceRepository.findByEmployee_IdAndLeaveType_IdAndYear(entity.getEmployee().getId(),entity.getLeaveType(),Year.of(year));
			LeaveBalanceEntity leaveBalanceEntity = optional.get();
			long between = ChronoUnit.DAYS.between(entity.getStartDate(),entity.getEndDate());
			leaveBalanceEntity.setAvailableDays(leaveBalanceEntity.getAvailableDays().add(BigDecimal.valueOf(between)));
			LeaveRequestEntity save = leaveRequestRepository.save(entity);
			leaveBalanceRepository.save(leaveBalanceEntity);
			return new ApiResponse(true,"success",save.toDto());
	    }
	    else {
		    	return new ApiResponse(false,"Unauthorized user",null);
		     }
	}
}

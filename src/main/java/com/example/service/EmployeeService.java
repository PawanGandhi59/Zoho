package com.example.service;

import java.util.List;
import java.util.Optional;
import com.example.repository.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ApiResponse;
import com.example.dto.DepartmentDto;
import com.example.dto.EmployeeDto;
import com.example.dto.EmployeeProjectionDto;
import com.example.entity.DepartmentEntity;
import com.example.entity.EmployeeEntity;
import com.example.entity.EmployeeStatus;
import com.example.entity.OrganizationEntity;
import com.example.repository.DepartmentRepository;
import com.example.repository.EmployeeRepository;

@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;
	private ModelMapper modelMapper;
	private DepartmentRepository departmentRepository;
	private DepartmentService departmentService;
	private OrganizationRepository organizationRepository;
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository,ModelMapper modelMapper,DepartmentRepository departmentRepository,
						   DepartmentService departmentService,OrganizationService organizationService, OrganizationRepository organizationRepository) {
		this.employeeRepository=employeeRepository;
		this.modelMapper=modelMapper;
		this.departmentRepository=departmentRepository;
		this.departmentService=departmentService;
		this.organizationRepository=organizationRepository;
	}
	
	public ApiResponse<EmployeeDto> save(EmployeeDto empDto,Long employeeId){
		EmployeeEntity map = modelMapper.map(empDto,EmployeeEntity.class);
		Optional<EmployeeEntity> empOpt =
		            employeeRepository.findByIdAndStatus(employeeId, EmployeeStatus.ACTIVE);
		
		    if (empOpt.isEmpty())
		    {
		        return new ApiResponse<>(false, "Assigner not found", null);
		    }

		    EmployeeEntity employee = empOpt.get();
		    if(!employee.getOrganizationId().getId().equals(empDto.getOrganizationId()))
		    	{return new ApiResponse(false,"Assigner does not belong to organization",null);}
		    Optional<DepartmentEntity> byIdAndStatus = departmentRepository.findByIdAndStatus(empDto.getDepartmentId(),"ACTIVE");
		    if(byIdAndStatus.isEmpty()) {return new ApiResponse(false,"Department not found or inactive",null);}
		    Optional<OrganizationEntity> byIdAndStatus2 = organizationRepository.findByIdAndStatus(empDto.getOrganizationId(),"ACTIVE");
		    if(byIdAndStatus2.isEmpty()) {return new ApiResponse(false,"Organization not found or inactive",null);}
		    if(employee.getDesignation().equalsIgnoreCase("hr") || employee.getDesignation().equalsIgnoreCase("ceo")) 
		    {
		    	ApiResponse<DepartmentDto> byOrgAndDept = departmentService.getByOrgAndDept(empDto.getOrganizationId(),empDto.getDepartmentId());
		    	if(!byOrgAndDept.isSuccess()) {return new ApiResponse(false,byOrgAndDept.getMessage(),null);}
		    	map.setDeptId(byIdAndStatus.get());
		    	map.setOrganizationId(byIdAndStatus2.get());
		    	EmployeeEntity save = employeeRepository.save(map);
		    	return new ApiResponse(true,"success",toDto(save));
		    }
		    else {
		    	return new ApiResponse(false,"Unauthorized user",null);
		    }
	} 
	
	public ApiResponse<List<EmployeeDto>> getAllByOrg(Long orgId){
		Optional<OrganizationEntity> status = organizationRepository.findByIdAndStatus(orgId,"ACTIVE");
		if(status.isEmpty()) {return new ApiResponse(false,"Organization not found or inactive",null);}
		List<EmployeeEntity> allByStatus = employeeRepository.findAllByStatus(EmployeeStatus.ACTIVE);
		return new ApiResponse<List<EmployeeDto>>(true,"success",allByStatus.stream().map(ee->toDto(ee)).toList());
	}
	
	public List<EmployeeProjectionDto> getByDept(Long id){
		return employeeRepository.findEmployeesByDepartmentAndStatus(EmployeeStatus.ACTIVE, id);
		
	}
	
	public ApiResponse<EmployeeDto> getById(Long id){
		Optional<EmployeeEntity> byIdAndStatus = employeeRepository.findByIdAndStatus(id,EmployeeStatus.ACTIVE);
		if(byIdAndStatus.isEmpty()) {return new ApiResponse(false,"Employee not  found or inactive",null);}
		return new ApiResponse(true,"success",toDto(byIdAndStatus.get()));
	}
	
	public ApiResponse<EmployeeDto> updateDesignation(Long employeeId,Long changerId,String desName){
		Optional<EmployeeEntity> empOpt =
	            employeeRepository.findByIdAndStatus(employeeId, EmployeeStatus.ACTIVE);
		Optional<EmployeeEntity> byIdAndStatus = employeeRepository.findByIdAndStatus(changerId,EmployeeStatus.ACTIVE);
	
	    if (empOpt.isEmpty())
	    {
	        return new ApiResponse<>(false, "Assigner not found", null);
	    }
	    if(byIdAndStatus.isEmpty()) {return new ApiResponse<EmployeeDto>(false,"Employee not found or inactive",null);}

	    EmployeeEntity employeeEntity = empOpt.get();
	    EmployeeEntity changerEntity = byIdAndStatus.get();
	    if(!changerEntity.getOrganizationId().getId().equals(employeeEntity.getOrganizationId().getId()))
	    	{return new ApiResponse(false,"Assigner does not belong to organization",null);}
	
	
	 if(changerEntity.getDesignation().equalsIgnoreCase("hr") || changerEntity.getDesignation().equalsIgnoreCase("ceo")) 
	    {
		 employeeEntity.setDesignation(desName);
		 EmployeeEntity save = employeeRepository.save(employeeEntity);
		 return new ApiResponse<EmployeeDto>(true,"success",toDto(save));
	    }
	 else {
	    	return new ApiResponse(false,"Unauthorized user",null);
	    }
	}
	
	public ApiResponse<EmployeeDto> updateNumber(Long employeeId,Long changerId,String newNumber){
		Optional<EmployeeEntity> empOpt =
	            employeeRepository.findByIdAndStatus(employeeId, EmployeeStatus.ACTIVE);
		Optional<EmployeeEntity> byIdAndStatus = employeeRepository.findByIdAndStatus(changerId,EmployeeStatus.ACTIVE);
	
	    if (empOpt.isEmpty())
	    {
	        return new ApiResponse<>(false, "Assigner not found", null);
	    }
	    if(byIdAndStatus.isEmpty()) {return new ApiResponse<EmployeeDto>(false,"Employee not found or inactive",null);}

	    EmployeeEntity employeeEntity = empOpt.get();
	    EmployeeEntity changerEntity = byIdAndStatus.get();
	    if(!changerEntity.getOrganizationId().getId().equals(employeeEntity.getOrganizationId().getId()))
	    	{return new ApiResponse(false,"Assigner does not belong to organization",null);}
	
	
	 if(changerEntity.getDesignation().equalsIgnoreCase("hr") || changerEntity.getDesignation().equalsIgnoreCase("ceo") || employeeId.equals(changerId)) 
	    {
		 employeeEntity.setNumber(newNumber);
		 EmployeeEntity save = employeeRepository.save(employeeEntity);
		 return new ApiResponse<EmployeeDto>(true,"success",toDto(save));
	    }
	 else {
	    	return new ApiResponse(false,"Unauthorized user",null);
	    }
	}
	
	public ApiResponse<EmployeeDto> deactivate(Long employeeId,Long changerId,EmployeeStatus empstatus){
		Optional<EmployeeEntity> empOpt =
	            employeeRepository.findByIdAndStatus(employeeId, EmployeeStatus.ACTIVE);
		Optional<EmployeeEntity> byIdAndStatus = employeeRepository.findByIdAndStatus(changerId,EmployeeStatus.ACTIVE);
	
	    if (empOpt.isEmpty())
	    {
	        return new ApiResponse<>(false, "Assigner not found", null);
	    }
	    if(byIdAndStatus.isEmpty()) {return new ApiResponse<EmployeeDto>(false,"Employee not found or inactive",null);}

	    EmployeeEntity employeeEntity = empOpt.get();
	    EmployeeEntity changerEntity = byIdAndStatus.get();
	    if(!changerEntity.getOrganizationId().getId().equals(employeeEntity.getOrganizationId().getId()))
	    	{return new ApiResponse(false,"Assigner does not belong to organization",null);}
	
	
	 if(changerEntity.getDesignation().equalsIgnoreCase("hr") || changerEntity.getDesignation().equalsIgnoreCase("ceo")) 
	    {
		 employeeEntity.setStatus(empstatus);
		 EmployeeEntity save = employeeRepository.save(employeeEntity);
		 return new ApiResponse<EmployeeDto>(true,"success",toDto(save));
	    }
	 else {
	    	return new ApiResponse(false,"Unauthorized user",null);
	    }
	}
	
	
	
	
	
	
	
	
	
	
	private EmployeeDto toDto(EmployeeEntity employee) {
	    if (employee == null) return null;

	    EmployeeDto dto = new EmployeeDto();
	    dto.setId(employee.getId());
	    dto.setFname(employee.getFname());
	    dto.setLname(employee.getLname());
	    dto.setEmail(employee.getEmail());
	    dto.setDesignation(employee.getDesignation());
	    dto.setJoiningDate(employee.getJoiningDate());
	    dto.setStatus(employee.getStatus());
	    dto.setNumber(employee.getNumber());

	   
	    if (employee.getDeptId() != null) {
	        dto.setDepartmentId(employee.getDeptId().getId());
	    }

	    if (employee.getOrganizationId() != null) {
	        dto.setOrganizationId(employee.getOrganizationId().getId());
	    }

	    return dto;
	}

}

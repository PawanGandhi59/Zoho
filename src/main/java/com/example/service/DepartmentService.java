package com.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ApiResponse;
import com.example.dto.DepartmentDto;
import com.example.dto.DepartmentHodDto;
import com.example.dto.EmployeeDto;
import com.example.entity.DepartmentEntity;
import com.example.entity.DepartmentHodEntity;
import com.example.entity.EmployeeEntity;
import com.example.entity.EmployeeStatus;
import com.example.entity.OrganizationEntity;
import com.example.repository.DepartmentHodRepository;
import com.example.repository.DepartmentRepository;
import com.example.repository.EmployeeRepository;
import com.example.repository.OrganizationRepository;

@Service
public class DepartmentService {
	private DepartmentRepository departmentRepository;
	private DepartmentHodRepository departmentHodRepository;
	private OrganizationRepository organizationRepository;
	private EmployeeRepository employeeRepository;
	private ModelMapper modelMapper;
	@Autowired
	public DepartmentService(DepartmentRepository departmentRepository,DepartmentHodRepository departmentHodRepository,ModelMapper modelMapper,OrganizationRepository organizationRepository,EmployeeRepository employeeRepository) {
		this.departmentHodRepository=departmentHodRepository;
		this.departmentRepository=departmentRepository;
		this.organizationRepository=organizationRepository;
		this.employeeRepository=employeeRepository;
		this.modelMapper=modelMapper;
	}
	
	public ApiResponse<DepartmentDto> save(DepartmentDto dptDto,Long employeeId) {
		DepartmentEntity map = modelMapper.map(dptDto,DepartmentEntity.class);

	    Optional<EmployeeEntity> empOpt =
	            employeeRepository.findByIdAndStatus(employeeId, EmployeeStatus.ACTIVE);

	    if (empOpt.isEmpty())
	    {
	        return new ApiResponse<>(false, "Employee not found", null);
	    }

	    EmployeeEntity employee = empOpt.get();
	    if(employee.getOrganizationId().getId()!=dptDto.getOrganization()) 
	    	{return new ApiResponse(false,"Employee does not belong to organization",null);}
	    
	    if(employee.getDesignation().equalsIgnoreCase("hr") || employee.getDesignation().equalsIgnoreCase("ceo")) 
	    {
		    Optional<OrganizationEntity> byIdAndStatus = organizationRepository.findByIdAndStatus(dptDto.getOrganization(),"ACTIVE");
			if(byIdAndStatus.isEmpty()) {return new ApiResponse<DepartmentDto>(false,"Organization not found",null);}
			map.setOrganization(byIdAndStatus.get());
		    DepartmentEntity save = departmentRepository.save(map);
		    DepartmentDto dept=new DepartmentDto();
		    dept.setId(save.getId());
		    dept.setName(save.getName());
		    dept.setOrganization(byIdAndStatus.get().getId());
		    dept.setStatus(save.getStatus());
		    
		    return new ApiResponse<DepartmentDto>(true,"success",dept);
	    }
	    else {return new ApiResponse(false,"Unauthorized user to create department",null);}
	}
	
	public List<DepartmentDto> getAll(){
		List<DepartmentEntity> all = departmentRepository.findAll();
		return all.stream().map(entity->toDto(entity)).toList();
	}
	
	public Optional<DepartmentDto> get(long id){
		Optional<DepartmentEntity> byId = departmentRepository.findById(id);
		if(byId.isEmpty()) {return Optional.empty();}
		
		return byId.map(t ->toDto(t) );
	}
	
	public ApiResponse<List<DepartmentDto>> getByOrg(Long orgId){
		boolean existsById = organizationRepository.existsById(orgId);
		if(!existsById) {return new ApiResponse<List<DepartmentDto>>(false,"Organization not found",null);}
		List<DepartmentEntity> byOrganization_id = departmentRepository.findByOrganization_Id(orgId);
		List<DepartmentDto> list = byOrganization_id.stream().map(de->toDto(de)).toList();
		return new ApiResponse<List<DepartmentDto>>(true, "success",list);
	}
	
	public ApiResponse<DepartmentDto> getByOrgAndDept(Long orgId,Long deptId){
		boolean existsById = organizationRepository.existsById(orgId);
		if(!existsById) {return new ApiResponse<DepartmentDto>(false,"Organization not found",null);}
		Optional<DepartmentEntity> byId = departmentRepository.findById(deptId);
		if(byId.isEmpty()) {return new ApiResponse<DepartmentDto>(false, "Department not found", null);}
		   DepartmentEntity department = byId.get();
		    if (!department.getOrganization().getId().equals(orgId)) {
		        return new ApiResponse<>(false, "Department does not belong to organization", null);
		    }
		//Optional<DepartmentDto> map = byId.map(this::toDto);
		return new ApiResponse<DepartmentDto>(true, "success",toDto(department));
	}
	
	public ApiResponse<DepartmentDto> update(Long deptId,Long employeeId,String newName){
		Optional<DepartmentEntity> byId = departmentRepository.findByIdAndStatus(deptId,"ACTIVE");
		if(byId.isEmpty()) {return new ApiResponse<DepartmentDto>(false,"Department not found",null);}
		DepartmentEntity departmentEntity = byId.get();
		Optional<EmployeeEntity> empOpt =
		            employeeRepository.findByIdAndStatus(employeeId, EmployeeStatus.ACTIVE);

		    if (empOpt.isEmpty())
		    {
		        return new ApiResponse<>(false, "Employee not found", null);
		    }

		    EmployeeEntity employee = empOpt.get();
		    if (!employee.getOrganizationId().getId()
		            .equals(departmentEntity.getOrganization().getId())) {return new ApiResponse(false,"Employee does not belong to organization",null);}
		    
		    if(employee.getDesignation().equalsIgnoreCase("hr") || employee.getDesignation().equalsIgnoreCase("ceo")) 
		    {
			departmentEntity.setName(newName);
			DepartmentEntity save = departmentRepository.save(departmentEntity);
			return new ApiResponse<DepartmentDto>(true, "success",toDto(save));		
		    }
		    else {return new ApiResponse(false,"Unauthorized user",null);}
	}
	
	public ApiResponse<DepartmentDto> deactivate(Long deptId,Long employeeId){
		Optional<DepartmentEntity> byId = departmentRepository.findByIdAndStatus(deptId,"ACTIVE");
		if(byId.isEmpty()) {return new ApiResponse<DepartmentDto>(false,"Department not found",null);}
		DepartmentEntity departmentEntity = byId.get();
		Optional<EmployeeEntity> empOpt =
		            employeeRepository.findByIdAndStatus(employeeId, EmployeeStatus.ACTIVE);

		 if (empOpt.isEmpty())
		    {
		        return new ApiResponse<>(false, "Employee not found", null);
		    }

		    EmployeeEntity employee = empOpt.get();
		    if(!employee.getOrganizationId().getId().equals(departmentEntity.getOrganization().getId())) 
		    	{return new ApiResponse(false,"Employee does not belong to organization",null);}
		    
		    if(employee.getDesignation().equalsIgnoreCase("hr") || employee.getDesignation().equalsIgnoreCase("ceo")) 
		    {
		departmentEntity.setStatus("INACTIVE");
		DepartmentEntity save = departmentRepository.save(departmentEntity);
		return new ApiResponse<DepartmentDto>(true, "success",toDto(save));
		    }
		    else {return new ApiResponse(false,"Unauthorized user",null);}
	}
	
	public ApiResponse<DepartmentHodDto> assignHod(Long departmentId, Long employeeId,Long assignerId) {
		Optional<DepartmentEntity> status = departmentRepository.findByIdAndStatus(departmentId,"ACTIVE");
		if(status.isEmpty()) {return new ApiResponse<DepartmentHodDto>(false, "Department not found or inactive", null);}
		Optional<EmployeeEntity> status2 = employeeRepository.findByIdAndStatus(employeeId,EmployeeStatus.ACTIVE);
		if(status2.isEmpty()) {return new ApiResponse<DepartmentHodDto>(false, "Employee not found or inactive", null);}
		if (!status2.get().getOrganizationId().getId()
		        .equals(status.get().getOrganization().getId())) {
		    return new ApiResponse<>(false, "HOD must belong to same organization", null);
		}

		Optional<EmployeeEntity> empOpt =
	            employeeRepository.findByIdAndStatus(assignerId, EmployeeStatus.ACTIVE);

	 if (empOpt.isEmpty())
	    {
	        return new ApiResponse<>(false, "Employee not found", null);
	    }

	    EmployeeEntity employee = empOpt.get();
	    if(employee.getOrganizationId().getId()!=status.get().getOrganization().getId()) 
	    	{return new ApiResponse(false,"Employee does not belong to organization",null);}
	    
	    if(employee.getDesignation().equalsIgnoreCase("hr") || employee.getDesignation().equalsIgnoreCase("ceo")) 
	    {
		    DepartmentHodEntity hod = new DepartmentHodEntity();
		    hod.setDepartment(status.get());
		    hod.setEmployee(status2.get());
		    hod.setStartDate(LocalDate.now());
		    DepartmentHodEntity save = departmentHodRepository.save(hod);
		    return new ApiResponse<DepartmentHodDto>(true, "success",toDto(save));
	    }
	    else {return new ApiResponse(false,"Unauthorized user",null);}
	}

	public ApiResponse<List<EmployeeDto>> getHods(Long departmentId) {

	    Optional<DepartmentEntity> department =
	            departmentRepository.findByIdAndStatus(departmentId, "ACTIVE");

	    if (department.isEmpty()) {
	        return new ApiResponse<>(
	                false,
	                "Department not found or inactive",
	                null
	        );
	    }

	    List<EmployeeDto> hods = departmentHodRepository
	            .findByDepartment_Id(departmentId)
	            .stream()
	            .map(hod -> toDto(hod.getEmployee()))
	            .toList();

	    return new ApiResponse<>(
	            true,
	            "success",
	            hods
	    );
	}
	
	private DepartmentDto toDto(DepartmentEntity entity) {
	    DepartmentDto dto = new DepartmentDto();
	    dto.setId(entity.getId());
	    dto.setName(entity.getName());
	    dto.setStatus(entity.getStatus());
	    dto.setOrganization(entity.getOrganization().getId());
	    return dto;
	}

	private DepartmentHodDto toDto(DepartmentHodEntity entity) {

	    if (entity == null) {
	        return null;
	    }

	    DepartmentHodDto dto = new DepartmentHodDto();
	    dto.setId(entity.getId());
	    dto.setStartDate(entity.getStartDate());
	    dto.setEndDate(entity.getEndDate());

	    // SAFE: only access IDs
	    if (entity.getDepartment() != null) {
	        dto.setDepartmentId(entity.getDepartment().getId());
	    }

	    if (entity.getEmployee() != null) {
	        dto.setEmployeeId(entity.getEmployee().getId());
	    }

	    return dto;
	}
	
	private EmployeeDto toDto(EmployeeEntity entity) {

	    if (entity == null) {
	        return null;
	    }

	    EmployeeDto dto = new EmployeeDto();

	    dto.setId(entity.getId());
	    dto.setFname(entity.getFname());
	    dto.setLname(entity.getLname());
	    dto.setEmail(entity.getEmail());
	    dto.setDesignation(entity.getDesignation());
	    dto.setJoiningDate(entity.getJoiningDate());
	    dto.setStatus(entity.getStatus());
	    dto.setNumber(entity.getNumber());

	    // SAFE mapping (IDs only)
	    if (entity.getDeptId() != null) {
	        dto.setDepartmentId(entity.getDeptId().getId());
	    }

	    if (entity.getOrganizationId() != null) {
	        dto.setOrganizationId(entity.getOrganizationId().getId());
	    }

	    return dto;
	}


}

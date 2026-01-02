package com.example.service;

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
	
	public ApiResponse<DepartmentDto> save(DepartmentDto dptDto) {
		DepartmentEntity map = modelMapper.map(dptDto,DepartmentEntity.class);
	    Optional<OrganizationEntity> byIdAndStatus = organizationRepository.findByIdAndStatus(map.getOrganization().getId(),"ACTIVE");
		if(byIdAndStatus.isEmpty()) {return new ApiResponse<DepartmentDto>(false,"Organization not found",null);}
	    DepartmentEntity save = departmentRepository.save(map);
	    return new ApiResponse<DepartmentDto>(true,"success",modelMapper.map(save,DepartmentDto.class));
	}
	
	public List<DepartmentDto> getAll(){
		List<DepartmentEntity> all = departmentRepository.findAll();
		return all.stream().map(de->modelMapper.map(de,DepartmentDto.class)).toList();
	}
	
	public Optional<DepartmentDto> get(long id){
		Optional<DepartmentEntity> byId = departmentRepository.findById(id);
		if(byId.isEmpty()) {return Optional.empty();}
		DepartmentEntity departmentEntity = byId.get();
		return Optional.of(modelMapper.map(departmentEntity,DepartmentDto.class));
	}
	
	public ApiResponse<List<DepartmentDto>> getByOrg(Long orgId){
		boolean existsById = organizationRepository.existsById(orgId);
		if(!existsById) {return new ApiResponse<List<DepartmentDto>>(false,"Organization not found",null);}
		List<DepartmentEntity> byOrganization_id = departmentRepository.findByOrganization_Id(orgId);
		List<DepartmentDto> list = byOrganization_id.stream().map(de->modelMapper.map(de,DepartmentDto.class)).toList();
		return new ApiResponse<List<DepartmentDto>>(true, "success",list);
	}
	
	public ApiResponse<DepartmentDto> getByOrgAndDept(Long orgId,Long deptId){
		boolean existsById = organizationRepository.existsById(orgId);
		if(!existsById) {return new ApiResponse<DepartmentDto>(false,"Organization not found",null);}
		 Optional<DepartmentEntity> byId = departmentRepository.findById(deptId);
		if(byId.isEmpty()) {return new ApiResponse<DepartmentDto>(false, "Department not found", null);}
		return new ApiResponse<DepartmentDto>(true, "success",modelMapper.map(byId.get(), DepartmentDto.class));
	}
	
	public ApiResponse<DepartmentDto> update(Long deptId,String newName){
		Optional<DepartmentEntity> byId = departmentRepository.findByIdAndStatus(deptId,"ACTIVE");
		if(byId.isEmpty()) {return new ApiResponse<DepartmentDto>(false,"Department not found",null);}
		DepartmentEntity departmentEntity = byId.get();
		departmentEntity.setName(newName);
		DepartmentEntity save = departmentRepository.save(departmentEntity);
		return new ApiResponse<DepartmentDto>(true, "success",modelMapper.map(save,DepartmentDto.class));		
	}
	
	public ApiResponse<DepartmentDto> deactivate(Long deptId){
		Optional<DepartmentEntity> byId = departmentRepository.findByIdAndStatus(deptId,"ACTIVE");
		if(byId.isEmpty()) {return new ApiResponse<DepartmentDto>(false,"Department not found",null);}
		DepartmentEntity departmentEntity = byId.get();
		departmentEntity.setStatus("INACTIVE");
		DepartmentEntity save = departmentRepository.save(departmentEntity);
		return new ApiResponse<DepartmentDto>(true, "success",modelMapper.map(save,DepartmentDto.class));
	}
	
	public ApiResponse<DepartmentHodDto> assignHod(Long departmentId, Long employeeId) {
		Optional<DepartmentEntity> status = departmentRepository.findByIdAndStatus(departmentId,"ACTIVE");
		if(status.isEmpty()) {return new ApiResponse<DepartmentHodDto>(false, "Department not found or inactive", null);}
		Optional<EmployeeEntity> status2 = employeeRepository.findByIdAndStatus(employeeId,EmployeeStatus.ACTIVE);
		if(status2.isEmpty()) {return new ApiResponse<DepartmentHodDto>(false, "Employee not found or inactive", null);}
	    DepartmentHodEntity hod = new DepartmentHodEntity();
	    hod.setDepartment(status.get());
	    hod.setEmployee(status2.get());
	    DepartmentHodEntity save = departmentHodRepository.save(hod);
	    return new ApiResponse<DepartmentHodDto>(true, "success", modelMapper.map(save,DepartmentHodDto.class));
	}

	public List<EmployeeDto> getHods(Long departmentId) {
	    return departmentHodRepository.findByDepartment_Id(departmentId)
	        .stream()
	        .map(hod -> modelMapper.map(hod.getEmployee(), EmployeeDto.class))
	        .toList();
	}

}

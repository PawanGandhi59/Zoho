package com.example.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.dto.ApiResponse;
import com.example.dto.DepartmentDto;
import com.example.dto.ProjectAssignmentDto;
import com.example.dto.ProjectDto;
import com.example.entity.DepartmentEntity;
import com.example.entity.EmployeeEntity;
import com.example.entity.EmployeeStatus;
import com.example.entity.OrganizationEntity;
import com.example.entity.ProjectAssignmentEntity;
import com.example.entity.ProjectEntity;
import com.example.repository.DepartmentRepository;
import com.example.repository.EmployeeRepository;
import com.example.repository.OrganizationRepository;
import com.example.repository.ProjectAssignmentRepository;
import com.example.repository.ProjectRepository;

@Service
public class ProjectService {
	private ProjectRepository projectRepository;
	private ModelMapper modelMapper;
	private OrganizationRepository organizationRepository;
	private EmployeeRepository employeeRepository;
	private DepartmentRepository departmentRepository;
	private ProjectAssignmentRepository projectAssignmentRepository;
	public ProjectService(ProjectRepository projectRepository,ModelMapper modelMapper,OrganizationRepository organizationRepository,EmployeeRepository employeeRepository,DepartmentRepository departmentRepository,ProjectAssignmentRepository projectAssignmentRepository){
		this.modelMapper=modelMapper;
		this.projectRepository=projectRepository;
		this.organizationRepository=organizationRepository;
		this.employeeRepository=employeeRepository;
		this.departmentRepository=departmentRepository;
		this.projectAssignmentRepository=projectAssignmentRepository;
	}
	
	public ApiResponse<ProjectDto> create(ProjectDto projectDto,Long creatorID){
		Optional<OrganizationEntity> orgStatus = organizationRepository.findByIdAndStatus(projectDto.getOrganization(),"ACTIVE");
		if(orgStatus.isEmpty()) {return new ApiResponse(false,"organization not found or inactive",null);}
		
		Optional<EmployeeEntity> ownerStatus = employeeRepository.findByIdAndStatus(projectDto.getOwner(),EmployeeStatus.ACTIVE);
		if(ownerStatus.isEmpty()) {return new ApiResponse(false,"owner of project not found or inactive",null);}
		EmployeeEntity employeeEntity = ownerStatus.get();
		if(!employeeEntity.getOrganizationId().getId().equals(projectDto.getOrganization())) {return new ApiResponse(false,"Owner does not belonng to organization",null);}
		Optional<DepartmentEntity> departmentStatus = departmentRepository.findByIdAndStatus(employeeEntity.getDeptId().getId(),"ACTIVE");
		if(departmentStatus.isEmpty()) {return new ApiResponse(false,"Department of owner not found or is inactive",null);}
		Optional<EmployeeEntity> creatorStatus = employeeRepository.findByIdAndStatus(creatorID,EmployeeStatus.ACTIVE);
		if(creatorStatus.isEmpty()) {return new ApiResponse(false,"creator not found or inactive",null);}
		if(!creatorStatus.get().getOrganizationId().getId().equals(projectDto.getOrganization())) {return new ApiResponse(false,"creator does not belong to department",null);}
		if(creatorStatus.get().getDesignation().equalsIgnoreCase("hr") || creatorStatus.get().getDesignation().equalsIgnoreCase("ceo") || creatorStatus.get().getDesignation().equalsIgnoreCase("hod")) 
	    {
			ProjectEntity map = modelMapper.map(projectDto,ProjectEntity.class);
			map.setDepartment(departmentStatus.get());
			map.setOwner(employeeEntity);
			map.setOrganization(orgStatus.get());
			ProjectEntity save = projectRepository.save(map);
			return new ApiResponse(true,"success",save.toDto());
	    }
	    else {
	    	return new ApiResponse(false,"Unauthorized user",null);
	    }	
	}
	
	public ApiResponse<ProjectAssignmentDto> assign(ProjectAssignmentDto projectAssignmentDto,Long creatorId){
		Optional<ProjectEntity> projectStatus = projectRepository.findByIdAndEndDate(projectAssignmentDto.getProjectId(), null);
		if(projectStatus.isEmpty()) {return new ApiResponse(false,"Project not found",null);}
		ProjectEntity projectEntity = projectStatus.get();
		Optional<EmployeeEntity> employeeStatus = employeeRepository.findByIdAndStatus(projectAssignmentDto.getEmployeeId(),EmployeeStatus.ACTIVE);
		Optional<EmployeeEntity> creatorStatus = employeeRepository.findByIdAndStatus(creatorId,EmployeeStatus.ACTIVE);
		if(employeeStatus.isEmpty()) {return new ApiResponse(false,"Employee not found or inactive",null);}
		if(creatorStatus.isEmpty()) {return new ApiResponse(false,"Assigner not found or inactive",null);}
		EmployeeEntity  employeeEntity= employeeStatus.get();
		EmployeeEntity  creatorEntity= creatorStatus.get();
		if(!employeeEntity.getOrganizationId().getId().equals(projectEntity.getOrganization().getId())) {return new ApiResponse(false,"Employee does not belonng to organization",null);}
		if(!creatorEntity.getOrganizationId().getId().equals(projectEntity.getOrganization().getId())) {return new ApiResponse(false,"Creator does not belonng to organization",null);}
		if(creatorStatus.get().getDesignation().equalsIgnoreCase("hr") || creatorStatus.get().getDesignation().equalsIgnoreCase("ceo") || creatorStatus.get().getDesignation().equalsIgnoreCase("hod")) 
	    {
			ProjectAssignmentEntity map = modelMapper.map(projectAssignmentDto,ProjectAssignmentEntity.class);
			map.setProjectId(projectEntity);
			map.setEmployeeID(employeeEntity);
			ProjectAssignmentEntity save = projectAssignmentRepository.save(map);
			return new ApiResponse(true,"success",save.toDto());
	    }
	    else {
	    	return new ApiResponse(false,"Unauthorized user",null);
	    }	
	}
}

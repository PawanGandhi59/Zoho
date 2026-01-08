package com.example.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ApiResponse;
import com.example.dto.JobDto;
import com.example.entity.EmployeeEntity;
import com.example.entity.EmployeeStatus;
import com.example.entity.JobEntity;
import com.example.entity.ProjectAssignmentEntity;
import com.example.entity.ProjectEntity;
import com.example.repository.EmployeeRepository;
import com.example.repository.JobRepository;
import com.example.repository.ProjectRepository;

@Service
public class JobService {
	private JobRepository jobRepository;
	private ProjectRepository projectRepository;
	private EmployeeRepository employeeRepository;
	private ModelMapper modelMapper;
	@Autowired
	public JobService(JobRepository jobRepository,ProjectRepository projectRepository,EmployeeRepository employeeRepository,ModelMapper modelMapper) {
		this.jobRepository=jobRepository;
		this.projectRepository=projectRepository;
		this.employeeRepository=employeeRepository;
		this.modelMapper=modelMapper;
	}
	
	public ApiResponse<JobDto> create(JobDto jobDto,Long creatorID){
		Optional<ProjectEntity> projectStatus = projectRepository.findByIdAndEndDate(jobDto.getProject(), null);
		if(projectStatus.isEmpty()) {return new ApiResponse(false,"Project not found",null);}
		Optional<EmployeeEntity> creatorStatus = employeeRepository.findByIdAndStatus(creatorID,EmployeeStatus.ACTIVE);
		if(creatorStatus.isEmpty()){return new ApiResponse(false,"Creator not found or inactive",null);}
		EmployeeEntity entity = creatorStatus.get();
		if(!entity.getOrganizationId().getId().equals(projectStatus.get().getOrganization().getId())) {return new ApiResponse(false,"Creator not belong to organization",null);}
		if(creatorStatus.get().getDesignation().equalsIgnoreCase("hr") || creatorStatus.get().getDesignation().equalsIgnoreCase("ceo") || creatorStatus.get().getDesignation().equalsIgnoreCase("hod")) 
	    {
			JobEntity map = modelMapper.map(jobDto,JobEntity.class);
			map.setProject(projectStatus.get());
			JobEntity save = jobRepository.save(map);
			return new ApiResponse(true,"success",save.toDto());
	    }
	    else {
	    	return new ApiResponse(false,"Unauthorized user",null);
	    }	
	}
}

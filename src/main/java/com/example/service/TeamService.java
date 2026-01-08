package com.example.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.ApiResponse;
import com.example.dto.EmployeeDto;
import com.example.dto.TeamDto;
import com.example.dto.TeamMemberDto;
import com.example.entity.EmployeeEntity;
import com.example.entity.EmployeeStatus;
import com.example.entity.OrganizationEntity;
import com.example.entity.TeamEntity;
import com.example.entity.TeamMemberEntity;
import com.example.entity.TeamStatus;
import com.example.repository.EmployeeRepository;
import com.example.repository.OrganizationRepository;
import com.example.repository.TeamMemberRepository;
import com.example.repository.TeamRepository;

@Service
public class TeamService {
	private TeamRepository teamRepository;
	private TeamMemberRepository teamMemberRepository;
	private OrganizationRepository organizationRepository;
	private EmployeeRepository employeeRepository;
	private ModelMapper modelMapper;
	@Autowired
	public TeamService(TeamRepository teamRepository,TeamMemberRepository teamMemberRepository,OrganizationRepository organizationRepository,EmployeeRepository employeeRepository,ModelMapper modelMapper) {
		this.teamMemberRepository=teamMemberRepository;
		this.teamRepository=teamRepository;
		this.employeeRepository=employeeRepository;
		this.organizationRepository=organizationRepository;
		this.modelMapper=modelMapper;
	}
	
	public ApiResponse<TeamDto> create(TeamDto teamDto,Long creatorId){
		Optional<OrganizationEntity> orgStatus = organizationRepository.findByIdAndStatus(teamDto.getOrganizationId(), "ACTIVE");
		if(orgStatus.isEmpty()) {return new ApiResponse(false,"Organization not found or inactive",null);}
		Optional<EmployeeEntity> empStatus = employeeRepository.findByIdAndStatus(teamDto.getLeadId(),EmployeeStatus.ACTIVE);
		if(empStatus.isEmpty()) {return new ApiResponse(false,"Lead not found or inactive",null);}
		Optional<EmployeeEntity> creatorStatus = employeeRepository.findByIdAndStatus(creatorId,EmployeeStatus.ACTIVE);
		if(creatorStatus.isEmpty()) {return new ApiResponse(false,"Assigner not found or inactive",null);}
		OrganizationEntity organizationEntity = orgStatus.get();
		EmployeeEntity leadEntity = empStatus.get();
		EmployeeEntity creatorEntity = creatorStatus.get();
		if(!leadEntity.getOrganizationId().getId().equals(teamDto.getOrganizationId())) {return new ApiResponse(false,"Lead does not belong to organization",null);}
		if(!creatorEntity.getOrganizationId().getId().equals(teamDto.getOrganizationId())) {return new ApiResponse(false,"Team creator does not belong to organization",null);}
		TeamEntity map = modelMapper.map(teamDto,TeamEntity.class);
		if(creatorEntity.getDesignation().equalsIgnoreCase("hr") || creatorEntity.getDesignation().equalsIgnoreCase("ceo")) 
	    {
			map.setOrganizationId(organizationEntity);
			map.setLeadId(leadEntity);
			TeamEntity save = teamRepository.save(map);
			return new ApiResponse(true,"success",toDto(save));
	    }
	 else {
	    	return new ApiResponse(false,"Unauthorized user",null);
	    }
	}
	
	public ApiResponse<TeamMemberDto> assignMember(TeamMemberDto teamMemberDto,Long creatorId){
		boolean status = teamMemberRepository.existsByTeam_IdAndEmployee_Id(teamMemberDto.getTeamId(),teamMemberDto.getEmployeeId());
		if(status) {return new ApiResponse(false,"Member already exists",null);}
		Optional<TeamEntity> teamStatus = teamRepository.findByIdAndStatus(teamMemberDto.getTeamId(),TeamStatus.ACTIVE);
		if(teamStatus.isEmpty()) {return new ApiResponse(false,"Team not found or inactive",null);}
		Optional<EmployeeEntity> employeeStatus = employeeRepository.findByIdAndStatus(teamMemberDto.getEmployeeId(),EmployeeStatus.ACTIVE);
		if(employeeStatus.isEmpty()) {return new ApiResponse(false,"Employee not found or inactive",null);}
		Optional<EmployeeEntity> creatorStatus = employeeRepository.findByIdAndStatus(creatorId,EmployeeStatus.ACTIVE);
		if(creatorStatus.isEmpty()) {return new ApiResponse(false,"Assigner not found or inactive",null);}
		EmployeeEntity creatorEntity = creatorStatus.get();
		TeamEntity teamEntity = teamStatus.get();
		EmployeeEntity employeeEntity = employeeStatus.get();
		if(!teamEntity.getOrganizationId().getId().equals(employeeEntity.getOrganizationId().getId())) {return new ApiResponse(false,"Employee does not belong to organization",null);}
		if(!creatorEntity.getOrganizationId().getId().equals(teamEntity.getOrganizationId().getId())) {return new ApiResponse(false,"Assigner does not belong to organization",null);}
		TeamMemberEntity map = modelMapper.map(teamMemberDto,TeamMemberEntity.class);
		if(creatorEntity.getDesignation().equalsIgnoreCase("hr") || creatorEntity.getDesignation().equalsIgnoreCase("ceo")) 
	    {
			map.setTeam(teamEntity);
			map.setEmployee(employeeEntity);
			TeamMemberEntity save = teamMemberRepository.save(map);
			return new ApiResponse(true,"success",toDto(save));
	    }
	 else {
	    	return new ApiResponse(false,"Unauthorized user",null);
	    }
	}
	
	
	@Transactional
	public ApiResponse<TeamMemberDto> removeMember(TeamMemberDto teamMemberDto,Long creatorId){
		boolean exists = teamMemberRepository.existsByTeam_IdAndEmployee_Id(teamMemberDto.getTeamId(),teamMemberDto.getEmployeeId());
		if(!exists) {return new ApiResponse(false,"Either team id or member id is wrong",null);}
		Optional<EmployeeEntity> creatorStatus = employeeRepository.findByIdAndStatus(creatorId,EmployeeStatus.ACTIVE);
		if(creatorStatus.get().getDesignation().equalsIgnoreCase("hr") || creatorStatus.get().getDesignation().equalsIgnoreCase("ceo")) 
	    {
		TeamMemberEntity teamMemberEntity = teamMemberRepository.deleteByTeam_IdAndEmployee_Id(teamMemberDto.getTeamId(),teamMemberDto.getEmployeeId());
		return new ApiResponse(true,"success",toDto(teamMemberEntity));
	    }
	    else {
		    	return new ApiResponse(false,"Unauthorized user",null);
		     }
	}
	
	public ApiResponse<TeamDto> deactivate(Long teamId,Long creatorId){
		Optional<TeamEntity> teamStatus = teamRepository.findByIdAndStatus(teamId,TeamStatus.ACTIVE);
		if(teamStatus.isEmpty()) {return new ApiResponse(false,"Team not found or inactive",null);}
		Optional<EmployeeEntity> creatorStatus = employeeRepository.findByIdAndStatus(creatorId,EmployeeStatus.ACTIVE);
		if(creatorStatus.isEmpty()) {return new ApiResponse(false,"deactivator not found or inactive",null);}
		if(creatorStatus.get().getDesignation().equalsIgnoreCase("hr") || creatorStatus.get().getDesignation().equalsIgnoreCase("ceo")) 
	    {
			TeamEntity teamEntity = teamStatus.get();
			teamEntity.setStatus(TeamStatus.INACTIVE);
			TeamEntity save = teamRepository.save(teamEntity);
			return new ApiResponse(true,"success",toDto(teamEntity));
	    }
	    else {
		    	return new ApiResponse(false,"Unauthorized user",null);
		     }
	}
	
	private TeamMemberDto toDto(TeamMemberEntity entity) {

	    TeamMemberDto dto = new TeamMemberDto();

	    dto.setTeamId(entity.getTeam().getId());
	    dto.setEmployeeId(entity.getEmployee().getId());
	    return dto;
	}

	
	private TeamDto toDto(TeamEntity team) {

	    TeamDto dto = new TeamDto();

	    dto.setId(team.getId());
	    dto.setName(team.getName());

	    if (team.getLeadId() != null) {
	        dto.setLeadId(team.getLeadId().getId());
	    }
	    
	    if (team.getOrganizationId() != null) {
	        dto.setOrganizationId(team.getOrganizationId().getId());
	    }

	    return dto;
	}
	
	
}

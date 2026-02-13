package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.OrgRequestDto;
import com.example.dto.OrganizationDto;
import com.example.entity.EmployeeEntity;
import com.example.entity.OrganizationEntity;
import com.example.entity.RoleEntity;
import com.example.repository.EmployeeRepository;
import com.example.repository.OrganizationRepository;
import com.example.repository.RoleRepository;

@Service
public class OrganizationService {
	private OrganizationRepository organizationRepository;
	private ModelMapper modelMapper;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	public OrganizationService(OrganizationRepository organizationRepository,ModelMapper modelMapper) {
		this.organizationRepository=organizationRepository;
		this.modelMapper=modelMapper;
	}
	
	public OrganizationDto save(OrgRequestDto  orgDto) {
		OrganizationEntity organizationEntity =new OrganizationEntity();
		organizationEntity.setCreated_at(orgDto.getCreated_at());
		organizationEntity.setDomain(orgDto.getDomain());
		organizationEntity.setName(orgDto.getName());
		organizationEntity.setStatus(orgDto.getStatus());
		organizationEntity.setTimezone(orgDto.getTimezone());
		OrganizationEntity save = organizationRepository.save(organizationEntity);
		
		RoleEntity owner=new RoleEntity("ROLE_OWNER",save);
		RoleEntity admin=new RoleEntity("ROLE_ADMIN",save);
		RoleEntity roleemployee=new RoleEntity("ROLE_EMPLOYEE",save);
		roleRepository.saveAll(List.of(owner,admin,roleemployee));
		
		EmployeeEntity employee=new EmployeeEntity();
		employee.setDesignation(orgDto.getDesignation());
		employee.setEmail(orgDto.getEmail());
		employee.setFname(orgDto.getFname());
		employee.setJoiningDate(orgDto.getJoiningDate());
		employee.setLname(orgDto.getLname());
		employee.setNumber(orgDto.getNumber());
		employee.setOrganizationId(save);
		employee.setPassword(passwordEncoder.encode(orgDto.getPassword()));
		employee.setRoles(List.of(owner));
		employee.setStatus(orgDto.getEmployeeStatus());
		employeeRepository.save(employee);
		return modelMapper.map(save, OrganizationDto.class);
	}
	
	public List<OrganizationDto> getorganizations(){
		List<OrganizationEntity> list = organizationRepository.findAll();
		List<OrganizationDto> list2 = list.stream().map(oe->modelMapper.map(oe,OrganizationDto.class)).toList();
		return list2;
	}
	
	public Optional<OrganizationDto> get(Long id){
		Optional<OrganizationEntity> byId = organizationRepository.findById(id);
		if(byId.isEmpty()) {return Optional.empty();}
		OrganizationEntity entity = byId.get();
	    OrganizationDto map = modelMapper.map(entity,OrganizationDto.class);
	    return Optional.of(map);
	}
	
	public Optional<OrganizationDto> deactivate(Long id){
		Optional<OrganizationEntity> byId = organizationRepository.findById(id);
		if(byId.isEmpty()) {return Optional.empty();}
		OrganizationEntity organizationEntity = byId.get();
		organizationEntity.setStatus("INACTIVE");
		OrganizationEntity save = organizationRepository.save(organizationEntity);
		OrganizationDto map = modelMapper.map(save,OrganizationDto.class);
		return Optional.of(map);
		
	}
}

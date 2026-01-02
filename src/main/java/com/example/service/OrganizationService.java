package com.example.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.OrganizationDto;
import com.example.entity.OrganizationEntity;
import com.example.repository.OrganizationRepository;

@Service
public class OrganizationService {
	private OrganizationRepository organizationRepository;
	private ModelMapper modelMapper;
	@Autowired
	public OrganizationService(OrganizationRepository organizationRepository,ModelMapper modelMapper) {
		this.organizationRepository=organizationRepository;
		this.modelMapper=modelMapper;
	}
	
	public OrganizationDto save(OrganizationDto  orgDto) {
		OrganizationEntity organizationEntity = modelMapper.map(orgDto,OrganizationEntity.class);
		OrganizationEntity save = organizationRepository.save(organizationEntity);
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

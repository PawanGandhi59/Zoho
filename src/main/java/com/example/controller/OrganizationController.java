package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OrganizationDto;
import com.example.service.OrganizationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
	private OrganizationService organizationService;
	@Autowired
	public OrganizationController(OrganizationService organizationService) {
		this.organizationService=organizationService;
	}
	@PostMapping("/create")
	public ResponseEntity<OrganizationDto> save(@Valid @RequestBody OrganizationDto orgDto){
		OrganizationDto save = organizationService.save(orgDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrganizationDto> get(@PathVariable Long id){
		Optional<OrganizationDto> optional = organizationService.get(id);
		if(optional.isEmpty()) {return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(optional.get());
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<OrganizationDto>> getAll(){
		List<OrganizationDto> getorganizations = organizationService.getorganizations();
		return ResponseEntity.ok(getorganizations);
	}
	
	@PatchMapping("/deactivate/{id}")
	public ResponseEntity<OrganizationDto> deactivate(@PathVariable Long id){
		Optional<OrganizationDto> deactivate = organizationService.deactivate(id);
		if(deactivate.isEmpty()) {return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(deactivate.get());
	}
}

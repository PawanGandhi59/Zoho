package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ApiResponse;
import com.example.dto.EmployeeDto;
import com.example.dto.EmployeeProjectionDto;
import com.example.entity.EmployeeStatus;
import com.example.repository.EmployeeRepository;
import com.example.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
	private EmployeeService employeeService;
	@Autowired
	public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
		this.employeeService=employeeService;
		this.employeeRepository = employeeRepository;
	}
	
	@PostMapping("/create/{creatorId}")
	public ResponseEntity<ApiResponse<EmployeeDto>> create(@PathVariable("creatorId") Long id, @Valid @RequestBody EmployeeDto empDto){
		ApiResponse<EmployeeDto> save = employeeService.save(empDto, id);
		if(!save.isSuccess()) {return ResponseEntity.badRequest().body(save);}
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}
	
	@GetMapping("/getbyorg/{orgId}")
	public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAllByOrg(@PathVariable("orgId") Long id){
		ApiResponse<List<EmployeeDto>> allByOrg = employeeService.getAllByOrg(id);
		if(!allByOrg.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(allByOrg);}
		return ResponseEntity.ok(allByOrg);
	}
	
	
	@GetMapping("/getbydept/{deptId}")
	public ResponseEntity<List<EmployeeProjectionDto>> getAllByDept(@PathVariable("deptId") Long id){
		return ResponseEntity.ok(employeeService.getByDept(id));
	}
	
	@GetMapping("/getbyid/{empId}")
	public ResponseEntity<ApiResponse<EmployeeDto>> getByEmpId(@PathVariable("empId") Long id){
		ApiResponse<EmployeeDto> byId = employeeService.getById(id);
		if(!byId.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(byId);}
		return ResponseEntity.ok(byId);
	}
	
	@PutMapping("/updatedesignation/{empId}/{changerId}/{newName}")
	public ResponseEntity<ApiResponse<EmployeeDto>> updateDesignation(@PathVariable("empId") Long empId,
			                                                          @PathVariable("changerId") Long changerId,
			                                                          @PathVariable("newName") String  newName){
		ApiResponse<EmployeeDto> updateDesignation = employeeService.updateDesignation(empId, changerId, newName);
		if(!updateDesignation.isSuccess()) {return ResponseEntity.badRequest().body(updateDesignation);}
		return ResponseEntity.ok(updateDesignation);
	}
	
	@PutMapping("/updatenumber/{empId}/{changerId}/{newName}")
	public ResponseEntity<ApiResponse<EmployeeDto>> updateNumber(@PathVariable("empId") Long empId,
			                                                          @PathVariable("changerId") Long changerId,
			                                                          @PathVariable("newName") String  newName){
		ApiResponse<EmployeeDto> updateNumber = employeeService.updateNumber(empId, changerId, newName);
		if(!updateNumber.isSuccess()) {return ResponseEntity.badRequest().body(updateNumber);}
		return ResponseEntity.ok(updateNumber);
	}
	
	@PutMapping("/deactivate/{empId}/{changerId}")
	public ResponseEntity<ApiResponse<EmployeeDto>> deactivate(@PathVariable("empId") Long empId,@PathVariable("changerId") Long changerId){
		ApiResponse<EmployeeDto> deactivate = employeeService.deactivate(empId, changerId,EmployeeStatus.RESIGNED);
		if(!deactivate.isSuccess()) {
			return ResponseEntity.badRequest().body(deactivate);
		}
		return ResponseEntity.ok(deactivate);
	}
}

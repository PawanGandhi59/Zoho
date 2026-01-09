package com.example.controller;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ApiResponse;
import com.example.dto.LeaveBalanceDto;
import com.example.dto.LeaveRequestDto;
import com.example.dto.LeaveTypeDto;
import com.example.service.EmployeeService;
import com.example.service.LeaveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Leave")
public class LeaveController {

    private final EmployeeService employeeService;
	private LeaveService leaveService;
	@Autowired
	public LeaveController(LeaveService leaveService, EmployeeService employeeService) {
		this.leaveService=leaveService;
		this.employeeService = employeeService;
	}
	@PostMapping("/addleavetype")
	public ResponseEntity<ApiResponse<LeaveTypeDto>> createLeaveType(@Valid @RequestBody LeaveTypeDto leaveTypeDto){
		ApiResponse<LeaveTypeDto> response = leaveService.createLeaveType(leaveTypeDto);
		if(!response.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/addleavebalance")
	public ResponseEntity<ApiResponse<LeaveBalanceDto>> createLeaveBalance(@Valid @RequestBody LeaveBalanceDto leaveBalanceDto){
		leaveBalanceDto.setYear(Year.now());
		ApiResponse<LeaveBalanceDto> response = leaveService.addLeaveBalance(leaveBalanceDto);
		if(!response.isSuccess()) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/request")
	public ResponseEntity<ApiResponse<LeaveRequestDto>> leaveRequest(@Valid @RequestBody LeaveRequestDto leaveRequestDto){
		ApiResponse<LeaveRequestDto> requestLeave = leaveService.requestLeave(leaveRequestDto);
		if(!requestLeave.isSuccess()) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(requestLeave);}
		return ResponseEntity.status(HttpStatus.CREATED).body(requestLeave);
	}
	@PutMapping("/approve/{requestId}/{approverId}")
	public ResponseEntity<ApiResponse<LeaveRequestDto>> approve(@PathVariable("requestId")Long requestId,@PathVariable("approverId")Long approverId){
		ApiResponse<LeaveRequestDto> approveRequest = leaveService.approveRequest(requestId, approverId);
		if(!approveRequest.isSuccess()) {return ResponseEntity.badRequest().body(approveRequest);}
		return ResponseEntity.ok(approveRequest);
	}
	
	@PutMapping("/reject/{requestId}/{approverId}")
	public ResponseEntity<ApiResponse<LeaveRequestDto>> reject(@PathVariable("requestId")Long requestId,@PathVariable("approverId")Long approverId){
		ApiResponse<LeaveRequestDto> approveRequest = leaveService.rejectRequest(requestId, approverId);
		if(!approveRequest.isSuccess()) {return ResponseEntity.badRequest().body(approveRequest);}
		return ResponseEntity.ok(approveRequest);
	}
}

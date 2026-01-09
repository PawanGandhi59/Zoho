package com.example.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.example.dto.ApiResponse;
import com.example.dto.TimeLogDto;
import com.example.service.TimeLogService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;

@RestController
@RequestMapping("/Timelog")
public class TimeLogController {
	private TimeLogService timeLogService;
	public TimeLogController(TimeLogService timeLogService) {
		this.timeLogService=timeLogService;
	}
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<TimeLogDto>> create(@Valid @RequestBody TimeLogDto timeLogDto){
		ApiResponse<TimeLogDto> apiResponse = timeLogService.create(timeLogDto);
		if(!apiResponse.isSuccess()) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);}
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}
	
	@GetMapping("/getmonthly/{empId}")
	public ResponseEntity<List<TimeLogDto>> getMonthlyLogs(@PathVariable("empId") Long empId){
		List<TimeLogDto> monthlyLogs = timeLogService.getMonthlyLogs(empId,LocalDate.now());
		return ResponseEntity.ok(monthlyLogs);
	}
	
	@PutMapping("/edit")
	public ResponseEntity<ApiResponse<TimeLogDto>> edit(@Valid @RequestBody TimeLogDto timeLogDto){
		ApiResponse<TimeLogDto> apiResponse = timeLogService.edit(timeLogDto);
		if(!apiResponse.isSuccess()) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);}
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}
	
	@DeleteMapping("/delete/{logId}")
	public ResponseEntity<ApiResponse<String>> delete(@PathVariable("logId") Long logId){
		ApiResponse<String> delete = timeLogService.delete(logId);
		if(!delete.isSuccess()) {return ResponseEntity.badRequest().body(delete);}
		return ResponseEntity.ok(delete);
	}
}

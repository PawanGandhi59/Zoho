package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ApiResponse;
import com.example.dto.TimeSheetDto;
import com.example.service.TimeSheetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Timesheet")
public class TimeSheetController {
	private TimeSheetService timeSheetService;
	public TimeSheetController(TimeSheetService timeSheetService) {
		this.timeSheetService=timeSheetService;
	}
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<TimeSheetDto>> create(@Valid @RequestBody TimeSheetDto timeSheetDto){
		ApiResponse<TimeSheetDto> apiResponse = timeSheetService.create(timeSheetDto);
		if(!apiResponse.isSuccess()) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);}
		return ResponseEntity.ok(apiResponse);
	}
}

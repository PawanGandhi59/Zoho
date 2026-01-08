package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ApiResponse;
import com.example.dto.JobDto;
import com.example.service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Jobs")
public class JobController {
	private JobService jobService;
	public JobController(JobService jobService) {
		this.jobService=jobService;
	}
	@PostMapping("/create/{creatorId}")
	public ResponseEntity<ApiResponse<JobDto>> create(@PathVariable("creatorId") Long  creatorId,@Valid @RequestBody JobDto jobDto){
		ApiResponse<JobDto> apiResponse = jobService.create(jobDto, creatorId);
		if(!apiResponse.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);}
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}
}

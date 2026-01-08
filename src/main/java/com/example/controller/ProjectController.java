package com.example.controller;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ApiResponse;
import com.example.dto.ProjectAssignmentDto;
import com.example.dto.ProjectDto;
import com.example.repository.ProjectRepository;
import com.example.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
	private ProjectService projectService;
	@Autowired
	public ProjectController(ProjectService projectService, ProjectRepository projectRepository) {
		this.projectService=projectService;
		this.projectRepository = projectRepository;
	}
	
	@PostMapping("/create/{creatorId}")
	public ResponseEntity<ApiResponse<ProjectDto>> create(@PathVariable("creatorId")Long creatorId,@Valid @RequestBody ProjectDto projectDto){
		ApiResponse<ProjectDto> apiResponse = projectService.create(projectDto, creatorId);
		if(!apiResponse.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);}
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}	
	
	@PostMapping("/assign/{creatorId}")
	public ResponseEntity<ApiResponse<ProjectAssignmentDto>> assign(@PathVariable("creatorId")Long creatorId,@Valid @RequestBody ProjectAssignmentDto projectAssignmentDto){
		ApiResponse<ProjectAssignmentDto> assign = projectService.assign(projectAssignmentDto, creatorId);
		if(!assign.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(assign);}
		return ResponseEntity.status(HttpStatus.CREATED).body(assign);
	}
}


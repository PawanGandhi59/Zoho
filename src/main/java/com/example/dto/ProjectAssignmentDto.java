package com.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProjectAssignmentDto {
	@NotNull(message="Project id is required")
	@Positive(message = "Project id must be > 0")
	private Long projectId;
	@NotNull(message="Employee id is required")
	@Positive(message = "Employee id must be > 0")
	private Long employeeId;
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
}

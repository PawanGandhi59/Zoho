package com.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TeamMemberDto {
	@NotNull(message="Team id is required")
	@Positive(message="Team id  must be > 0")
	private Long teamId;
	@NotNull(message="Employee id is required")
	@Positive(message="Employee id  must be > 0")
	private Long employeeId;

	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
}

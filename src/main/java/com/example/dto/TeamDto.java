package com.example.dto;

import com.example.entity.TeamStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TeamDto {
	private Long id;
	@NotBlank(message="Team name is needed")
	@Size(min = 2, max = 100, message = "Team name must be between 2 and 100 characters")
	private String name;
	@NotNull(message="Team Lead id is required")
	@Positive(message="Team Lead must be > 0")
	private Long leadId;
	@NotNull(message="Organizatio id id is required")
	@Positive(message="Organization id must be > 0")
	private Long organizationId;
	@NotNull(message = "Status is required")
	private TeamStatus status=TeamStatus.ACTIVE;
	public Long getLeadId() {
		return leadId;
	}
	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

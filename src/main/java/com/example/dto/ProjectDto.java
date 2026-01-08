package com.example.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public class ProjectDto {
	private Long id;
	@NotBlank(message="Name of project is required")
	private String name;
	private String description;
    @NotNull(message = "Strat date of project is required")
    @PastOrPresent(message = "Strat date of project cannot be in the future")
	private LocalDate startDate;
    @PastOrPresent(message = "End date of project cannot be in the future")
	private LocalDate endDate;
    @NotNull(message="Project owner id is required")
    @Positive(message="Owner id must be > 0")
	private Long owner;
    @NotNull(message="Project owner organization id is required")
    @Positive(message="organization id must be > 0")
    private Long organization;
    private Long department;
	public Long getOrganization() {
		return organization;
	}
	public void setOrganization(Long organization) {
		this.organization = organization;
	}
	public Long getDepartment() {
		return department;
	}
	public void setDepartment(Long department) {
		this.department = department;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public Long getOwner() {
		return owner;
	}
	public void setOwner(Long owner) {
		this.owner = owner;
	}
}

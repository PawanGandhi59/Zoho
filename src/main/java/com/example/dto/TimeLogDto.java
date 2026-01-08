package com.example.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.entity.BillableStatus;
import com.example.entity.TimeLogStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public class TimeLogDto {
	
	private Long id;
	@NotNull(message="Employee id is required")
	@Positive(message = "Employee id must be > 0")
	private Long employeeId;
	@NotNull(message="Projecct id is required")
	@Positive(message = "Project id must be > 0")
	private Long projectID;
	@NotNull(message="Work date is required")
	@PastOrPresent(message="Work date cannot be in future")
	private LocalDate workDate;
	@NotNull(message="Hours are required")
	@Positive(message = "Hours must be greater than 0")
	private BigDecimal hours;
	private String description;
	private TimeLogStatus status=TimeLogStatus.PENDING;
	@NotNull(message ="Job id is required")
	@Positive(message = "Job id must be > 0")
	private Long jobId;
	@NotNull(message ="Billable status is required")
	private BillableStatus billableStatus;
	@NotBlank(message="Work item is required")
	private String workItem;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Long getProjectID() {
		return projectID;
	}
	public void setProjectID(Long projectID) {
		this.projectID = projectID;
	}
	public LocalDate getWorkDate() {
		return workDate;
	}
	public void setWorkDate(LocalDate workDate) {
		this.workDate = workDate;
	}
	public BigDecimal getHours() {
		return hours;
	}
	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public TimeLogStatus getStatus() {
		return status;
	}
	public void setStatus(TimeLogStatus status) {
		this.status = status;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public BillableStatus getBillableStatus() {
		return billableStatus;
	}
	public void setBillableStatus(BillableStatus billableStatus) {
		this.billableStatus = billableStatus;
	}
	public String getWorkItem() {
		return workItem;
	}
	public void setWorkItem(String workItem) {
		this.workItem = workItem;
	}
}

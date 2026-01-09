package com.example.dto;

import java.math.BigDecimal;
import java.time.Year;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public class LeaveBalanceDto {
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@NotNull
	@Positive
	private Long employeeId;
	@NotNull
	@Positive
	private Long leaveTypeId;
	private BigDecimal availableDays;
	private Year year;
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Long getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(Long leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public BigDecimal getAvailableDays() {
		return availableDays;
	}
	public void setAvailableDays(BigDecimal availableDays) {
		this.availableDays = availableDays;
	}
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	} 
}

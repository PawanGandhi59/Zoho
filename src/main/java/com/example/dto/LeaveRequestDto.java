package com.example.dto;

import java.time.LocalDate;

import com.example.entity.LeaveRequestStatus;
import com.example.entity.LeaveTypeStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public class LeaveRequestDto {
	private Long id;
	@NotNull
	@Positive
	private Long employeeId;
	@NotNull
	private Long leaveType;
	@NotNull
	private LocalDate startDate;
	@NotNull
	private LocalDate endDate;
	@NotNull
	private LeaveRequestStatus leaveStatus=LeaveRequestStatus.APPLIED;
	private String reason;
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
	public Long getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(Long leaveType) {
		this.leaveType = leaveType;
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
	public LeaveRequestStatus getLeaveStatus() {
		return leaveStatus;
	}
	public void setLeaveStatus(LeaveRequestStatus leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}

package com.example.dto;

import java.math.BigDecimal;

import com.example.entity.LeaveTypeStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LeaveTypeDto {
	private Long id;
	@NotNull
	private LeaveTypeStatus name;
	@NotNull
	@Positive
	private BigDecimal maxDaysPerYear;
	@NotNull
	@Positive
	private Long organizationId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LeaveTypeStatus getName() {
		return name;
	}
	public void setName(LeaveTypeStatus name) {
		this.name = name;
	}
	public BigDecimal getMaxDaysPerYear() {
		return maxDaysPerYear;
	}
	public void setMaxDaysPerYear(BigDecimal maxDaysPerYear) {
		this.maxDaysPerYear = maxDaysPerYear;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
}

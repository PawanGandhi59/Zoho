package com.example.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.entity.TimeSheetStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public class TimeSheetDto {

    private Long id;
    @NotNull
    @Positive
    private Long employeeId;
    @NotNull
    @PastOrPresent
    private LocalDate monthStart;
    private LocalDate monthEnd;
    @NotNull
    @Positive
    private BigDecimal totalHours;
    @NotNull
    private TimeSheetStatus status;
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
	public LocalDate getMonthStart() {
		return monthStart;
	}
	public void setMonthStart(LocalDate monthStart) {
		this.monthStart = monthStart;
	}
	public LocalDate getMonthEnd() {
		return monthEnd;
	}
	public void setMonthEnd(LocalDate monthEnd) {
		this.monthEnd = monthEnd;
	}
	public BigDecimal getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(BigDecimal totalHours) {
		this.totalHours = totalHours;
	}
	public TimeSheetStatus getStatus() {
		return status;
	}
	public void setStatus(TimeSheetStatus status) {
		this.status = status;
	}
}


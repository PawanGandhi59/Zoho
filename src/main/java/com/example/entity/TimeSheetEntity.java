package com.example.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.dto.TimeSheetDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "timesheet")
public class TimeSheetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @Column(name = "total_hours")
    private BigDecimal totalHours;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeSheetStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmployeeEntity getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeEntity employee) {
		this.employee = employee;
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
	
	public TimeSheetDto toDto() {
		TimeSheetDto dto=new TimeSheetDto();
		dto.setEmployeeId(this.employee.getId());
		dto.setId(this.id);
		dto.setStatus(this.status);
		dto.setTotalHours(this.totalHours);
		return dto;
	}
}


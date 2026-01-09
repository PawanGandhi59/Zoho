package com.example.entity;

import java.math.BigDecimal;
import java.time.Year;

import com.example.dto.LeaveBalanceDto;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_balance")
public class LeaveBalanceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_type_id")
    private LeaveTypeEntity leaveType;

    @Column(name = "available_days")
    private BigDecimal availableDays;
    @Column(name="year")
    private Year year;

    public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public void setEmployee(EmployeeEntity employee) {
		this.employee = employee;
	}

	public void setLeaveType(LeaveTypeEntity leaveType) {
		this.leaveType = leaveType;
	}

	public LeaveBalanceEntity() {}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmployeeEntity getEmployee() {
        return employee;
    }

    public LeaveTypeEntity getLeaveType() {
        return leaveType;
    }

    public BigDecimal getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(BigDecimal availableDays) {
        this.availableDays = availableDays;
    }
    
    public LeaveBalanceDto toDto() {
    	LeaveBalanceDto dto=new LeaveBalanceDto();
    	dto.setAvailableDays(this.availableDays);
    	dto.setEmployeeId(this.employee.getId());
    	dto.setLeaveTypeId(this.leaveType.getId());
    	dto.setYear(this.year);
    	dto.setId(this.id);
    	return dto;
    }
}

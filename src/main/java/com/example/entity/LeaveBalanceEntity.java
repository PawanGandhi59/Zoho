package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_balance")
public class LeaveBalanceEntity {

    @EmbeddedId
    private LeaveBalanceId id;

    public void setId(LeaveBalanceId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("leaveTypeId")
    @JoinColumn(name = "leave_type_id")
    private LeaveTypeEntity leaveType;

    @Column(name = "available_days")
    private Double availableDays;

    public LeaveBalanceEntity() {}

    public LeaveBalanceEntity(EmployeeEntity employee, LeaveTypeEntity leaveType, Double availableDays) {
        this.employee = employee;
        this.leaveType = leaveType;
        this.availableDays = availableDays;
        this.id = new LeaveBalanceId(employee.getId(), leaveType.getId());
    }

    public LeaveBalanceId getId() {
        return id;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public LeaveTypeEntity getLeaveType() {
        return leaveType;
    }

    public Double getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Double availableDays) {
        this.availableDays = availableDays;
    }
}

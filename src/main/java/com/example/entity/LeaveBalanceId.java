package com.example.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LeaveBalanceId implements Serializable {

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "leave_type_id")
    private Long leaveTypeId;

    // Required by JPA
    public LeaveBalanceId() {}

    public LeaveBalanceId(Long employeeId, Long leaveTypeId) {
        this.employeeId = employeeId;
        this.leaveTypeId = leaveTypeId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeaveBalanceId)) return false;
        LeaveBalanceId that = (LeaveBalanceId) o;
        return Objects.equals(employeeId, that.employeeId)
                && Objects.equals(leaveTypeId, that.leaveTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, leaveTypeId);
    }
}

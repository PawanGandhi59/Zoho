package com.example.entity;

import java.time.LocalDate;

import com.example.dto.LeaveRequestDto;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_request")
public class LeaveRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;


    @Column(name = "leave_type")
    private Long leaveType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "reason")
    private String reason;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LeaveRequestStatus status;

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


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public LeaveRequestStatus getStatus() {
		return status;
	}


	public void setStatus(LeaveRequestStatus status) {
		this.status = status;
	}

	public LeaveRequestDto toDto() {
		LeaveRequestDto dto=new LeaveRequestDto();
		dto.setEmployeeId(this.employee.getId());
		dto.setEndDate(this.endDate);
		dto.setId(this.id);
		dto.setLeaveStatus(this.status);
		dto.setLeaveType(this.leaveType);
		dto.setReason(this.reason);
		dto.setStartDate(this.startDate);
		dto.setEndDate(this.endDate);
		return dto;
	}

 
    }



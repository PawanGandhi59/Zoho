package com.example.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.dto.TimeLogDto;

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
@Table(name="time_log")
public class TimeLogEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="employee_id")
	private EmployeeEntity employeeId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="project_id")
	private ProjectEntity projectId;
	@Column(name="work_date")
	private LocalDate workDate;
	@Column(name="hours")
	private BigDecimal hours;
	@Column(name="description")
	private String description;
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private TimeLogStatus status;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="job_id")
	private JobEntity jobId;
	@Enumerated(EnumType.STRING)
	@Column(name="billable_status")
	private BillableStatus billableStatus;
	@Column(name="work_item")
	private String workItem;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EmployeeEntity getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(EmployeeEntity employeeId) {
		this.employeeId = employeeId;
	}
	public ProjectEntity getProjectId() {
		return projectId;
	}
	public void setProjectId(ProjectEntity projectId) {
		this.projectId = projectId;
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
	public JobEntity getJobId() {
		return jobId;
	}
	public void setJobId(JobEntity jobId) {
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
	
	public TimeLogDto toDto() {
		TimeLogDto dto=new TimeLogDto();
		dto.setBillableStatus(this.billableStatus);
		dto.setDescription(this.description);
		dto.setEmployeeId(this.employeeId.getId());
		dto.setHours(this.hours);
		dto.setId(this.id);
		dto.setJobId(this.jobId.getId());
		dto.setProjectID(this.projectId.getId());
		dto.setStatus(this.status);
		dto.setWorkDate(this.workDate);
		dto.setWorkItem(this.workItem);
		return dto;
	}
	
}

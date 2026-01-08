package com.example.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProjectAssignmentId implements Serializable {
	public ProjectAssignmentId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProjectAssignmentId(Long projectId, Long employeeId) {
		super();
		this.projectId = projectId;
		this.employeeId = employeeId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(employeeId, projectId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectAssignmentId other = (ProjectAssignmentId) obj;
		return Objects.equals(employeeId, other.employeeId) && Objects.equals(projectId, other.projectId);
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	@Column(name="project_id")
	private Long projectId;
	@Column(name="employee_id")
	private Long employeeId;
}

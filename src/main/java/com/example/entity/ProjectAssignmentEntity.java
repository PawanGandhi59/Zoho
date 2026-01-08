package com.example.entity;

import com.example.dto.ProjectAssignmentDto;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="project_assignment")
public class ProjectAssignmentEntity {

    @EmbeddedId
    private ProjectAssignmentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private ProjectEntity projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employeeID;

	public ProjectAssignmentId getId() {
		return id;
	}

	public void setId(ProjectAssignmentId id) {
		this.id = id;
	}

	public ProjectEntity getProjectId() {
		return projectId;
	}

	public void setProjectId(ProjectEntity projectId) {
		this.projectId = projectId;
	}

	public EmployeeEntity getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(EmployeeEntity employeeID) {
		this.employeeID = employeeID;
	}
	
	public ProjectAssignmentDto toDto() {
		ProjectAssignmentDto projectAssignmentDto=new ProjectAssignmentDto();
		projectAssignmentDto.setProjectId(this.projectId.getId());
		projectAssignmentDto.setEmployeeId(this.employeeID.getId());
		return projectAssignmentDto;
	}
}

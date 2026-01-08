package com.example.entity;

import java.time.LocalDate;
import java.util.List;

import com.example.dto.ProjectDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="project")
public class ProjectEntity {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	@Column(name="start_date")
	private LocalDate startDate;
	@Column(name="end_date")
	private LocalDate endDate;
	@JoinColumn(name="owner_id")
	@ManyToOne(fetch = FetchType.LAZY)
	
	private EmployeeEntity owner;
	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<JobEntity> jobs;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id")
	private OrganizationEntity organization;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department")
	private DepartmentEntity department;

	public OrganizationEntity getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}
	public DepartmentEntity getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}
	public List<JobEntity> getJobs() {
		return jobs;
	}
	public void setJobs(List<JobEntity> jobs) {
		this.jobs = jobs;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public EmployeeEntity getOwner() {
		return owner;
	}
	public void setOwner(EmployeeEntity owner) {
		this.owner = owner;
	} 
	


	public ProjectDto toDto() {
	    ProjectDto dto = new ProjectDto();

	    dto.setId(this.id);
	    dto.setName(this.name);
	    dto.setDescription(this.description);
	    dto.setStartDate(this.startDate);
	    dto.setEndDate(this.endDate);

	    // Owner
	    if (this.owner != null) {
	        dto.setOwner(this.owner.getId());
	    }

	    // Organization
	    if (this.organization != null) {
	        dto.setOrganization(this.organization.getId());
	    }





	    return dto;
	}
}



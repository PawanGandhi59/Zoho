package com.example.entity;

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
@Table(name = "team")
public class TeamEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="name")
	private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id")
	private EmployeeEntity leadId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organization_id")
    private OrganizationEntity organizationId;
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private TeamStatus status;
	public TeamStatus getStatus() {
		return status;
	}
	public void setStatus(TeamStatus status) {
		this.status = status;
	}
	public EmployeeEntity getLeadId() {
		return leadId;
	}
	public void setLeadId(EmployeeEntity leadId) {
		this.leadId = leadId;
	}
	public OrganizationEntity getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(OrganizationEntity organizationId) {
		this.organizationId = organizationId;
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

}

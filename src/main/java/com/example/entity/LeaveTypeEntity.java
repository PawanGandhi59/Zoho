package com.example.entity;

import java.math.BigDecimal;

import com.example.dto.LeaveTypeDto;

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
@Table(name="leave_type")
public class LeaveTypeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="name")
	@Enumerated(EnumType.STRING)
	private LeaveTypeStatus name;
	@Column(name="max_days_per_year")
	private BigDecimal maxDaysPerYear;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="organization_id")
	private OrganizationEntity organizationId;
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
	public LeaveTypeStatus getName() {
		return name;
	}
	public void setName(LeaveTypeStatus name) {
		this.name = name;
	}

	
	public BigDecimal getMaxDaysPerYear() {
		return maxDaysPerYear;
	}
	public void setMaxDaysPerYear(BigDecimal maxDaysPerYear) {
		this.maxDaysPerYear = maxDaysPerYear;
	}
	public LeaveTypeDto toDto() {
		LeaveTypeDto dto=new LeaveTypeDto();
		dto.setId(this.id);
		dto.setMaxDaysPerYear(this.maxDaysPerYear);
		dto.setName(this.name);
		dto.setOrganizationId(this.organizationId.getId());
		return dto;
	}
}

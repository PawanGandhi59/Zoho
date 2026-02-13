package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name="roles")
public class RoleEntity {
	public RoleEntity() {
	}

	public RoleEntity(String name, OrganizationEntity organizationId) {
		this.name = name;
		this.organizationId = organizationId;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="name")
	private String name;
	@ManyToOne
	@JoinColumn(name="organization_id")
	private OrganizationEntity 	organizationId;
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
	public OrganizationEntity getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(OrganizationEntity organizationId) {
		this.organizationId = organizationId;
	}
}

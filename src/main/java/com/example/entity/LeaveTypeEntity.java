package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private Integer max_days_per_year;
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
	public Integer getMax_days_per_year() {
		return max_days_per_year;
	}
	public void setMax_days_per_year(Integer max_days_per_year) {
		this.max_days_per_year = max_days_per_year;
	}
}

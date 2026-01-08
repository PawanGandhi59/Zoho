package com.example.dto;

public class EmployeeProjectionDto {
	private Long  id;
	private String  fullName;
	private String designation;
	private String number;
	private String department;
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EmployeeProjectionDto(Long id, String fullName, String designation,String number,String department) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.designation = designation;
		this.number=number;
		this.department=department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}

}

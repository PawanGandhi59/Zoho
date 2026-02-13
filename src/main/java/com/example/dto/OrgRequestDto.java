package com.example.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.example.entity.EmployeeStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrgRequestDto {

	    @NotBlank(message = "Organization name is required")
	    @Size(min = 1, max = 100, message = "Organization name must be between 1 and 100 characters")
	    private String name;

	    @NotBlank(message = "Domain is required")
	    @Size(max = 100, message = "Domain must not exceed 100 characters")
	    @Pattern(
	        regexp = "^[a-zA-Z0-9.-]+$",
	        message = "Domain can contain only letters, numbers, dots and hyphens"
	    )
	    private String domain;

	    @NotBlank(message = "Timezone is required")
	    @Pattern(regexp = "^[A-Za-z]+/[A-Za-z_]+$", message = "Timezone must be like 'Asia/Kolkata'")
	    @Size(max = 50, message = "Timezone must not exceed 50 characters")
	    private String timezone;
	    
	    private LocalDateTime created_at=LocalDateTime.now();
	    private String status="ACTIVE";
	    

	    @NotBlank(message = "First name is required")
	    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
	    @Pattern(
	        regexp = "^[A-Za-z]+$",
	        message = "First name can contain only letters"
	    )
	    private String fname;

	    @NotBlank(message = "Last name is required")
	    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
	    @Pattern(
	        regexp = "^[A-Za-z]+$",
	        message = "Last name can contain only letters"
	    )
	    private String lname;

	    @NotBlank(message = "Email is required")
	    @Email(message = "Invalid email format")
	    private String email;

	    @NotBlank(message = "Designation is required")
	    @Size(min = 2, max = 100, message = "Designation must be between 2 and 100 characters")
	    private String designation;

	   
	    private Long departmentId;

	    @NotNull(message = "Date of joining is required")
	    @PastOrPresent(message = "Date of joining cannot be in the future")
	    private LocalDate joiningDate;

	    @NotNull(message = "Employee status is required")
	    private EmployeeStatus employeeStatus=EmployeeStatus.ACTIVE;

	    @NotBlank(message = "Phone number is required")
	    @Pattern(
	        regexp = "^[6-9]\\d{9}$",
	        message = "Phone number must be a valid 10-digit Indian number"
	    )
	    private String number;

	    @NotBlank
	    private String password;

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setEmployeeStatus(EmployeeStatus employeeStatus) {
			this.employeeStatus = employeeStatus;
		}

		public String getFname() {
			return fname;
		}

		public void setFname(String fname) {
			this.fname = fname;
		}

		public String getLname() {
			return lname;
		}

		public void setLname(String lname) {
			this.lname = lname;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}

		public Long getDepartmentId() {
			return departmentId;
		}

		public void setDepartmentId(Long departmentId) {
			this.departmentId = departmentId;
		}

		public LocalDate getJoiningDate() {
			return joiningDate;
		}

		public void setJoiningDate(LocalDate joiningDate) {
			this.joiningDate = joiningDate;
		}

		public EmployeeStatus getEmployeeStatus() {
			return employeeStatus;
		}

		public void setStatus(EmployeeStatus employeeStatus) {
			this.employeeStatus = employeeStatus;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDomain() {
			return domain;
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}

		public String getTimezone() {
			return timezone;
		}

		public void setTimezone(String timezone) {
			this.timezone = timezone;
		}

		public LocalDateTime getCreated_at() {
			return created_at;
		}

		public void setCreated_at(LocalDateTime created_at) {
			this.created_at = created_at;
		}

}

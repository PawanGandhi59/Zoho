package com.example.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrganizationDto {

    private Long id; 

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

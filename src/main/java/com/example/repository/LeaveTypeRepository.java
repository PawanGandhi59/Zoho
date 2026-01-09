package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.LeaveTypeEntity;
import com.example.entity.LeaveTypeStatus;


public interface LeaveTypeRepository extends JpaRepository<LeaveTypeEntity,Long>{
	boolean existsByOrganizationId_IdAndName(Long organizationId_Id, LeaveTypeStatus name);
}

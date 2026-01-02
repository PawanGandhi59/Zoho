package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.LeaveTypeEntity;

public interface LeaveTypeRepository extends JpaRepository<LeaveTypeEntity,Long>{

}

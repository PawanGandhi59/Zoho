package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.EmployeeEntity;
import java.util.List;
import java.util.Optional;

import com.example.entity.EmployeeStatus;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long>{
	Optional<EmployeeEntity> findByIdAndStatus(Long id, EmployeeStatus status);
}

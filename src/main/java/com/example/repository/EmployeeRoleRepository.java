package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.EmployeeRoleEntity;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRoleEntity,Long>{

}

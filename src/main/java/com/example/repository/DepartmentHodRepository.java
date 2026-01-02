package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.DepartmentHodEntity;

public interface DepartmentHodRepository extends JpaRepository<DepartmentHodEntity,Long>{
	  List<DepartmentHodEntity> findByDepartment_Id(Long departmentId);
}

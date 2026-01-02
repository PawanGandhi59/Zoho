package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.DepartmentEntity;
import java.util.List;
import java.util.Optional;


public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long>{
	List<DepartmentEntity> findByOrganization_Id(Long id);
	Optional<DepartmentEntity> findByIdAndStatus(Long id, String status);
}

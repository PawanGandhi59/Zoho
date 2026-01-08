package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.dto.EmployeeProjectionDto;
import com.example.entity.EmployeeEntity;
import java.util.List;
import java.util.Optional;

import com.example.entity.EmployeeStatus;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long>{
	Optional<EmployeeEntity> findByIdAndStatus(Long id, EmployeeStatus status);
	List<EmployeeEntity> findAllByStatus(EmployeeStatus status);
	@Query("""
			SELECT new com.example.dto.EmployeeProjectionDto(
			    e.id,
			    CONCAT(e.fname, ' ', e.lname),
			    e.designation,
			    e.number,
			    e.deptId.name
			)
			FROM EmployeeEntity e
			WHERE e.status = :status
			AND e.deptId.id=:deptId
			""")
			List<EmployeeProjectionDto> findEmployeesByDepartmentAndStatus(EmployeeStatus status,Long deptId);

}

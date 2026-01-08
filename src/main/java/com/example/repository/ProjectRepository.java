package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.ProjectEntity;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


public interface ProjectRepository extends JpaRepository<ProjectEntity,Long>{
	Optional<ProjectEntity> findByIdAndEndDate(Long id, LocalDate endDate);
}

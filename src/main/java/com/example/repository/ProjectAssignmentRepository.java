package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.ProjectAssignmentEntity;
import com.example.entity.ProjectAssignmentId;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignmentEntity,ProjectAssignmentId> {

}

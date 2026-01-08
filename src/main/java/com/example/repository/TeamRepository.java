package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.TeamEntity;
import java.util.List;
import java.util.Optional;

import com.example.entity.TeamStatus;


public interface TeamRepository extends JpaRepository<TeamEntity,Long>{
	Optional<TeamEntity> findByIdAndStatus(Long id, TeamStatus status);
}

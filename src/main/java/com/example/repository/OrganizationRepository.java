package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.OrganizationEntity;
import java.util.List;
import java.util.Optional;


public interface OrganizationRepository extends JpaRepository<OrganizationEntity,Long>{
	Optional<OrganizationEntity> findByIdAndStatus(Long id, String status);
}

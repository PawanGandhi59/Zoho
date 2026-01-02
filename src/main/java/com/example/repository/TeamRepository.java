package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity,Long>{

}

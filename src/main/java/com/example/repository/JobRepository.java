package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity,Long> {

}

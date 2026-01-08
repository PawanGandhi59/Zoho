package com.example.repository;

import java.time.LocalDate;
import java.util.Optional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.TimeSheetEntity;

public interface TimeSheetRepository extends JpaRepository<TimeSheetEntity, Long> {
	


}


package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.LeaveRequestEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.entity.LeaveRequestStatus;



public interface LeaveRequestRepository extends JpaRepository<LeaveRequestEntity,Long>{
	boolean existsByEmployee_IdAndLeaveTypeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
	        Long employeeId,
	        Long leaveType,
	        LocalDate endDate,
	        LocalDate startDate
	);
	Optional<LeaveRequestEntity> findByIdAndStatus(Long id, LeaveRequestStatus status);
}

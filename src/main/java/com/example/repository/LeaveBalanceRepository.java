package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.LeaveBalanceEntity;
import com.example.entity.LeaveTypeEntity;
import java.time.Year;
import java.util.List;
import java.util.Optional;




public interface LeaveBalanceRepository extends JpaRepository<LeaveBalanceEntity,Long> {
	boolean existsByEmployee_IdAndLeaveType_IdAndYear(Long employee_Id, Long leaveType, Year year);
	Optional<LeaveBalanceEntity> findByEmployee_IdAndLeaveType_IdAndYear(Long employee_Id, Long leaveType_Id, Year year);
}

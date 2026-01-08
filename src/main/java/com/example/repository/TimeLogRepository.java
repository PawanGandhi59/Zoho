package com.example.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.TimeLogEntity;
import com.example.entity.TimeLogStatus;

public interface TimeLogRepository extends JpaRepository<TimeLogEntity,Long>{
	  List<TimeLogEntity> findByEmployeeId_IdAndWorkDateBetween(
	            Long employeeId,
	            LocalDate start,
	            LocalDate end
	    );

	    @Query("""
	        SELECT COALESCE(SUM(t.hours),0)
	        FROM TimeLogEntity t
	        WHERE t.employeeId.id = :empId
	        AND t.workDate = :date
	    """)
	    BigDecimal totalHoursForDay(Long empId, LocalDate date);

		List<TimeLogEntity> findByEmployeeId_IdAndStatusAndWorkDateBetween(Long empId, TimeLogStatus pending,
				LocalDate monthStart, LocalDate monthEnd);
}

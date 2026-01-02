package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.LeaveBalanceEntity;
import com.example.entity.LeaveBalanceId;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalanceEntity,LeaveBalanceId> {

}

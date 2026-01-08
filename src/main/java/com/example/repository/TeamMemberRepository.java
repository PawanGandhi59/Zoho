package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.TeamMemberEntity;
import com.example.entity.TeamMemberId;

public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity,TeamMemberId>{
	boolean existsByTeam_IdAndEmployee_Id(Long team_Id, Long employee_Id);
	@Modifying
	@Transactional
	TeamMemberEntity deleteByTeam_IdAndEmployee_Id(Long team_Id, Long employee_Id);
}

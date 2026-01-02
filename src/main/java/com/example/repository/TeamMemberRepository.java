package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.TeamMemberEntity;
import com.example.entity.TeamMemberId;

public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity,TeamMemberId>{

}

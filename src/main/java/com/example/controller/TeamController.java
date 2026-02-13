package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ApiResponse;
import com.example.dto.TeamDto;
import com.example.dto.TeamMemberDto;
import com.example.entity.TeamMemberEntity;
import com.example.service.TeamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Teams")
public class TeamController {
	private TeamService teamService;
	public TeamController(TeamService teamService) {
		this.teamService=teamService;
	}
	@PreAuthorize("hasRole('SUPER_ADMIN','ADMIN','OWNER','HR','HOD','MANAGER','CEO') and #creatorId==principal.userId and #teamDto.organizationId==principal.orgId")
	@PostMapping("/create/{creatorId}")
	public ResponseEntity<ApiResponse<TeamDto>> create(@PathVariable("creatorId") Long creatorId,@Valid @RequestBody TeamDto teamDto){
		ApiResponse<TeamDto> teamResponse = teamService.create(teamDto, creatorId);
		if(!teamResponse.isSuccess()) {return ResponseEntity.badRequest().body(teamResponse);}
		return ResponseEntity.status(HttpStatus.CREATED).body(teamResponse);
	}
	@PreAuthorize("hasRole('SUPER_ADMIN','ADMIN','OWNER','HR','HOD','MANAGER','CEO') and #creatorId==principal.userId")
	@PostMapping("/assign/{creatorId}")
	public ResponseEntity<ApiResponse<TeamMemberDto>> assignMember(@PathVariable("creatorId") Long creatorId,@Valid @RequestBody TeamMemberDto teamMemberDto){
		ApiResponse<TeamMemberDto> assignMember = teamService.assignMember(teamMemberDto, creatorId);
		if(!assignMember.isSuccess()) {return ResponseEntity.badRequest().body(assignMember);}
		return ResponseEntity.ok(assignMember);
	}

	@PreAuthorize("hasRole('SUPER_ADMIN','ADMIN','OWNER','HR','HOD','MANAGER','CEO') and #creatorId==principal.userId")
	@DeleteMapping("/remove/{creatorId}")
	public ResponseEntity<ApiResponse<TeamMemberDto>> removeMember(@PathVariable("creatorId") Long creatorId,@Valid @RequestBody TeamMemberDto dto){
		ApiResponse<TeamMemberDto> removeMember = teamService.removeMember(dto, creatorId);
		if(!removeMember.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(removeMember);}
		return ResponseEntity.ok(removeMember);
	}

	@PreAuthorize("hasRole('SUPER_ADMIN','ADMIN','OWNER','HR','HOD','MANAGER','CEO') and #creatorId==principal.userId")
	@PutMapping("/deactivate/{teamId}/{creatorId}")
	public ResponseEntity<ApiResponse<TeamDto>> deactivate(@PathVariable("teamId") Long teamId,@PathVariable("creatorId") Long creatorId){
		ApiResponse<TeamDto> deactivate = teamService.deactivate(teamId, creatorId);
		if(!deactivate.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(deactivate);}
		return ResponseEntity.ok(deactivate);
	}
}

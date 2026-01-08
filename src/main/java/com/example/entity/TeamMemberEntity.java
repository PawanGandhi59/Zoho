package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "team_member")
public class TeamMemberEntity {

    @EmbeddedId
    private TeamMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;



    public TeamMemberId getId() {
		return id;
	}

	public void setId(TeamMemberId id) {
		this.id = id;
	}

	public void setTeam(TeamEntity team) {
		this.team = team;
	}

	public void setEmployee(EmployeeEntity employee) {
		this.employee = employee;
	}

	public TeamEntity getTeam() {
        return team;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }
}

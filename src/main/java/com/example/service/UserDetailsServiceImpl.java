package com.example.service;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.CustomPrincipal;
import com.example.entity.EmployeeEntity;
import com.example.entity.EmployeeStatus;
import com.example.repository.EmployeeRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<EmployeeEntity> employee = employeeRepository.findByEmailAndStatus(email,EmployeeStatus.ACTIVE);
		if(employee.isEmpty()) {throw new UsernameNotFoundException("User not found");}
		List<SimpleGrantedAuthority> list = employee.get().getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName())).toList();
		EmployeeEntity entity = employee.get();
		return new CustomPrincipal(entity.getId(),entity.getEmail(),entity.getPassword(),entity.getOrganizationId().getId(),list);
	}

}

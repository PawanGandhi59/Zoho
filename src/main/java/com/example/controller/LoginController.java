package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.LoginRequestDto;
import com.example.entity.CustomPrincipal;
import com.example.jwt.JwtUtil;

@RestController
@RequestMapping("/")
public class LoginController {
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/login")
	public ResponseEntity<String> userLogin(@RequestBody LoginRequestDto user){
		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
		Authentication authenticate = authManager.authenticate(token);
		boolean authenticated = authenticate.isAuthenticated();
		if(authenticated) {
			CustomPrincipal principle=(CustomPrincipal)authenticate.getPrincipal();
			List<String> list = authenticate.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
			String jwt = jwtUtil.generateToken(principle.getUserId(),principle.getEmail(),principle.getOrgId(),list);
			return ResponseEntity.ok(jwt);
		}
		else {
			return ResponseEntity.badRequest().body("Login failed");
		}
	}
}

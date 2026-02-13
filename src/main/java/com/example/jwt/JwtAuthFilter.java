package com.example.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.entity.CustomPrincipal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
				String header = request.getHeader("Authorization");
				if(header!=null && header.startsWith("Bearer ")) {
					String token=header.substring(7);
					if(token!=null) {
			            if (jwtUtil.isTokenValid(token)) {
			            	String email = jwtUtil.extractEmail(token);
			            	Long orgId = jwtUtil.extractOrgId(token);
			            	Long userId = jwtUtil.extractUserId(token);
			            	List<String> roles = jwtUtil.extractRoles(token);
			            	if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			            		List<SimpleGrantedAuthority> authorities =
			                        roles.stream()
			                             .map(SimpleGrantedAuthority::new)
			                             .toList();
			            		CustomPrincipal principle=new CustomPrincipal();
			            		principle.setEmail(email);
			            		principle.setOrgId(orgId);
			            		principle.setUserId(userId);
			            		
			                    UsernamePasswordAuthenticationToken authToken =
			                            new UsernamePasswordAuthenticationToken(
			                                    principle,
			                                    null,
			                                   authorities
			                            );
			                    SecurityContextHolder.getContext().setAuthentication(authToken);
			                }
			            }
				}

		
	}
		filterChain.doFilter(request, response);
  }
}

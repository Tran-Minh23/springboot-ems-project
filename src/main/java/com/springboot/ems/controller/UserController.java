package com.springboot.ems.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ems.dto.UserDto;
import com.springboot.ems.model.User;
import com.springboot.ems.service.impl.CustomUserDetailsService;
import com.springboot.ems.util.JwtUtil;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/api/register")
	public ResponseEntity<Map<String, Object>> saveUser(@RequestBody UserDto request) {
		Map<String, Object> response= new LinkedHashMap<String, Object>();
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		
		try {
			customUserDetailsService.saveUser(user);
		}
		catch(Exception e) {
			response.put("errorCode", 1);
			response.put("message", "Invalid username");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.put("errorCode", 0);
		response.put("data", user);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/api/login")
	public ResponseEntity<Map<String, Object>> generateToken(@RequestBody UserDto request) throws Exception {
		Map<String, Object> response= new LinkedHashMap<String, Object>();
		try {
		authenticationManager.authenticate(new 
					UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		}
		catch(Exception e) {
			response.put("errorCode", 1);
			response.put("message", "Invalid username or password");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.put("errorCode", 0);
		response.put("token", jwtUtil.generateToken(request.getUsername()));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

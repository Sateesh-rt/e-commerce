package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.LoginRequest;
import com.ecommerce.dto.UserProjection;
import com.ecommerce.model.User;
import com.ecommerce.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired 
	ObjectMapper mapper;

	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		User savedUser = userService.saveUser(user);
		
		return ResponseEntity.ok(savedUser);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
		UserProjection user = userService.login(loginRequest.getName(), loginRequest.getPassword(), loginRequest.getRole());
		System.out.println(user);
        
		if (user != null) {
		
			return ResponseEntity.ok(user);
			
		
        } 
			 ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials or role");
		
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
	}

}

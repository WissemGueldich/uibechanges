package com.tn.uib.uibechanges.controller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tn.uib.uibechanges.security.LoginRequest;
import com.tn.uib.uibechanges.security.jwt.JwtResponse;
import com.tn.uib.uibechanges.security.jwt.JwtUtils;
import com.tn.uib.uibechanges.service.UserService;


//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getMatricule(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		User userDetails = (User) authentication.getPrincipal();		
		Set<String> authorities = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toSet());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getUsername(),
												 authorities));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody com.tn.uib.uibechanges.model.User user) {
		
		return userService.addUser(user);
	}
}
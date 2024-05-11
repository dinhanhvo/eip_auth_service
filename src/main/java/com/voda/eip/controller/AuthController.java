package com.voda.eip.controller;

import com.voda.eip.exception.AppException;
import com.voda.eip.model.Role;
import com.voda.eip.model.RoleName;
import com.voda.eip.model.User;
import com.voda.eip.payload.ApiResponse;
import com.voda.eip.payload.JwtAuthenticationResponse;
import com.voda.eip.payload.LoginRequest;
import com.voda.eip.payload.SignUpRequest;
import com.voda.eip.security.JwtTokenProvider;
import com.voda.eip.repository.RoleRepository;
import com.voda.eip.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@GetMapping("/hello")
	public String hello() {
		return "Hello User, have a nice day.";
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				signUpRequest.getPassword(), signUpRequest.getSerialWeigher());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role not set."));

		user.setRoles(Collections.singleton(userRole));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();

//        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
		return ResponseEntity.ok(result);
	}

	@PostMapping("/me")
	public ResponseEntity<?> getUserInfo(@Valid @RequestParam("token") String token) {

		Long userId = tokenProvider.getUserIdFromJWT(token);
		User user = userRepository.findById(userId).get();

		return ResponseEntity.ok(user);
	}

	@PostMapping("/user/{userId}/change")
	public ResponseEntity<?> changeUserInfo(@PathVariable("userId") Long userId,
			@Valid @RequestParam("serialWeigher") String serialWeigher) {

		User user = userRepository.findById(userId).get();
		user.setSerialWeigher(serialWeigher);
		userRepository.save(user);
		User ret = new User();
		ret.setPassword(null);
		return ResponseEntity.ok(ret);
	}
}

package com.spring.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.Erole;
import com.spring.entity.Roles;
import com.spring.entity.UserEntity;
import com.spring.jwt.JwtTokenProvider;
import com.spring.payload.request.LoginRequest;
import com.spring.payload.request.SignupRequest;
import com.spring.payload.response.JwtResponse;
import com.spring.payload.response.MessageResponse;
import com.spring.security.CustomUserDetails;
import com.spring.service.impl.RolesService;
import com.spring.service.impl.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
		if (userService.existsByUserName(signupRequest.getUserName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: username is ready"));
		}
		if (userService.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: email is ready"));
		}
		signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		UserEntity userEntity = modelMapper.map(signupRequest, UserEntity.class);
		Set<String> strRoles = signupRequest.getListRoles();
		Set<Roles> listRoles = new HashSet<>();
		if (strRoles == null) {
			// User quyen mac dinh
			Roles userRole = rolesService.findByRoleName(Erole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found 1"));
			listRoles.add(userRole);
		} else {
			System.out.println(strRoles.toString());
			strRoles.forEach(role -> {
				
				switch (role) {
				case "admin":
					Roles adminRole = rolesService.findByRoleName(Erole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found 2"));
					listRoles.add(adminRole);
					break;
//				case "moderator":
//					Roles modRole = rolesService.findByRoleName(Erole.ROLE_MODERATOR)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found 3"));
//					listRoles.add(modRole);
				case "user":
					Roles userRole = rolesService.findByRoleName(Erole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found 4"));
					listRoles.add(userRole);
					break;
				}
			});
		}
		userEntity.setListRoles(listRoles);
		userService.saveOrUpdate(userEntity);
		return ResponseEntity.ok(new MessageResponse("User registered successfully"));
	}

	@PostMapping("/signin")
	 
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		CustomUserDetails customUserDetail = (CustomUserDetails) authentication.getPrincipal();

		// Sinh JWT tra ve client
		String jwt = jwtTokenProvider.generateToken(customUserDetail.getUsername());
		// Lay cac quyen cua user
		List<String> listRoles = customUserDetail.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(new JwtResponse(jwt, customUserDetail.getUsername(), customUserDetail.getEmail(),
				customUserDetail.getPhone(), listRoles));

	}

}

package com.spring.api;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.spring.dto.CustomUserDetailDTO;
import com.spring.dto.UserDTO;
import com.spring.entity.RefreshTokenEntity;
import com.spring.payload.request.AccessTokenReq;
import com.spring.payload.request.ReqRefreshToken;
import com.spring.payload.response.RespRefreshToken;
import com.spring.service.impl.RefreshTokenService;
import jakarta.persistence.Access;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.view.RedirectView;

import com.spring.entity.Erole;
import com.spring.entity.Roles;
import com.spring.entity.UserEntity;
import com.spring.jwt.JwtTokenProvider;
import com.spring.payload.request.LoginRequest;
import com.spring.payload.request.SignupRequest;
import com.spring.payload.response.JwtResponse;
import com.spring.payload.response.MessageResponse;
import com.spring.security.CustomUserDetails;
import com.spring.service.IClientService;
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
	private RefreshTokenService refreshTokenService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private IClientService clientService;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) throws URISyntaxException {
		if (userService.existsByUserName(signupRequest.getUserName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Username already exists"));
		}
		if (userService.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email already exists"));
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
					case "user":
						Roles userRole = rolesService.findByRoleName(Erole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found 4"));
						listRoles.add(userRole);
						break;
				}
			});
		}
		userEntity.setListRoles(listRoles);
		userEntity.setUserStatus(true);
		userEntity.setEnabled(false);
		userService.saveOrUpdate(userEntity);
		clientService.create(signupRequest);
		RedirectView redirect = new RedirectView();
		redirect.addStaticAttribute("email", userEntity.getEmail());
		redirect.setUrl("/verification");
		return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
				.header("Location", redirect.getUrl())
				.body(redirect.getStaticAttributes());
	}

	@PostMapping("/signin")

	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			CustomUserDetails customUserDetail = (CustomUserDetails) authentication.getPrincipal();
			// Sinh JWT tra ve client
			String jwt = jwtTokenProvider.generateToken(customUserDetail.getUsername());
			// Lay cac quyen cua user
			List<String> listRoles = customUserDetail.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());
			RefreshTokenEntity refreshToken = refreshTokenService.findByUser(customUserDetail.getUsername());
			if (refreshToken != null) {
				refreshToken = refreshTokenService.updateRefreshToken(refreshToken.getToken());
			} else {
				refreshToken = refreshTokenService.createRefreshToken(customUserDetail.getUsername());
			}
			CustomUserDetailDTO customUserDetailDTO = modelMapper.map(customUserDetail,CustomUserDetailDTO.class);
			return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(),customUserDetailDTO));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body(new MessageResponse("Account no active!"));
		}
	}
	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken (@RequestBody ReqRefreshToken req) {
		try {
		RefreshTokenEntity refreshToken = refreshTokenService.findByToken(req.getRefresh_token());
		if (refreshToken != null) {
			if(refreshToken.getExpiryDate().compareTo(Instant.now()) >= 0) {
				String access_token = jwtTokenProvider.generateToken(refreshToken.getUserEntity().getUserName());
				String newRefresh_token = refreshTokenService.updateRefreshToken(refreshToken.getToken()).getToken();
				return ResponseEntity.ok(new RespRefreshToken(newRefresh_token,access_token));
			} else {
				return  ResponseEntity.badRequest().body(new MessageResponse("logout"));
			}
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("logout"));
		}
		} catch (Exception e) {
		    return ResponseEntity.badRequest().body(new MessageResponse("logout"));
		}
	}
	@PostMapping("/getUserWithAccessToken")
	public ResponseEntity<?> getUserWithAccessToken (@RequestBody AccessTokenReq accessTokenReq) {
		try {
			String userName = jwtTokenProvider.getUserNameFromToken(accessTokenReq.getAccess_token());
			UserEntity user = userService.findByUserName(userName);
			return ResponseEntity.ok().body(modelMapper.map(user,UserDTO.class));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found!"));
		}
	}
}

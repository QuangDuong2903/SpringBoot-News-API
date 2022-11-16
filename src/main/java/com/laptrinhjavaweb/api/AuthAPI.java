package com.laptrinhjavaweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.api.input.LoginRequest;
import com.laptrinhjavaweb.api.input.RefreshTokenRequest;
import com.laptrinhjavaweb.api.output.LoginResponse;
import com.laptrinhjavaweb.api.output.RefreshTokenResponse;
import com.laptrinhjavaweb.entity.RefreshTokenEntity;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.jwt.JwtTokenProvider;
import com.laptrinhjavaweb.repository.RefreshTokenRepository;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.security.CustomUserDetails;
import com.laptrinhjavaweb.service.IRefreshTokenService;

@RestController
@RequestMapping("/auth")
public class AuthAPI {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private IRefreshTokenService refreshTokenService;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping(value = "/signin")
	public LoginResponse authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenProvider.generateToken(authentication);
		long userID = ((CustomUserDetails) authentication.getPrincipal()).getId();
		UserEntity user = userRepository.findById(userID).orElse(null);
		if (user.getRefreshToken() != null)
			return new LoginResponse(jwt, refreshTokenService.verifyExpiration(user.getRefreshToken()).getToken());
		return new LoginResponse(jwt, refreshTokenService.createRefreshToken(userID).getToken());
	}
	
	@PostMapping(value = "/refreshtoken")
	public ResponseEntity<?> refreshToken(@Validated @RequestBody RefreshTokenRequest refreshTokenRequest) {
		RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(refreshTokenRequest.getRefreshToken());
		if(refreshToken == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refresh token not found");
		if(refreshTokenService.isExpiredToken(refreshToken))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token was expired. Please make a new signin request");;
		String jwt = jwtTokenProvider.generateToken(refreshToken.getUser().getUserName());
		return ResponseEntity.ok().body(new RefreshTokenResponse(jwt, refreshToken.getToken()));
	}
}

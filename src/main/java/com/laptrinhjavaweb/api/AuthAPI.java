package com.laptrinhjavaweb.api;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.api.input.LoginRequest;
import com.laptrinhjavaweb.api.input.RefreshTokenRequest;
import com.laptrinhjavaweb.api.output.LoginResponse;
import com.laptrinhjavaweb.api.output.RefreshTokenResponse;
import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.dto.GoogleUserDTO;
import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.entity.RefreshTokenEntity;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.jwt.JwtTokenProvider;
import com.laptrinhjavaweb.oauth2.google.GoogleService;
import com.laptrinhjavaweb.repository.RefreshTokenRepository;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.security.CustomUserDetails;
import com.laptrinhjavaweb.service.IRefreshTokenService;
import com.laptrinhjavaweb.service.IUserService;

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

	@Autowired
	private GoogleService googleService;

	@Autowired
	private IUserService userService;

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
		if (refreshToken == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refresh token not found");
		if (refreshTokenService.isExpiredToken(refreshToken))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Refresh token was expired. Please make a new signin request");
		String jwt = jwtTokenProvider.generateToken(refreshToken.getUser().getUserName());
		long userID = refreshToken.getUser().getId();
		refreshTokenRepository.delete(refreshToken);
		return ResponseEntity.ok()
				.body(new RefreshTokenResponse(jwt, refreshTokenService.createRefreshToken(userID).getToken()));
	}

	@GetMapping("/google")
	public ResponseEntity<?> loginGoogle(@RequestParam("code") String code)
			throws ClientProtocolException, IOException {
		if (code == null || code.isEmpty())
			return ResponseEntity.badRequest().body(null);
		String accessToken = googleService.getToken(code);
		GoogleUserDTO googleUserDTO = googleService.getUserInfo(accessToken);
		UserEntity user = userRepository.findOneByEmailAndTypeAndStatus(googleUserDTO.getEmail(),SystemConstant.GOOGLE_ACCOUNT, SystemConstant.ACTIVE_STATUS);
		if (user == null) {
			UserDTO userDTO = userService.save(googleUserDTO);
			return ResponseEntity.ok().body(new RefreshTokenResponse(jwtTokenProvider.generateToken(userDTO.getUserName()), refreshTokenService.createRefreshToken(userDTO.getId()).getToken()));
		}
		String jwt = jwtTokenProvider.generateToken(user.getUserName());
		if (user.getRefreshToken() != null)
			return ResponseEntity.ok().body(new LoginResponse(jwt, refreshTokenService.verifyExpiration(user.getRefreshToken()).getToken()));
		return ResponseEntity.ok().body(new RefreshTokenResponse(jwt, refreshTokenService.createRefreshToken(user.getId()).getToken()));
	}
}
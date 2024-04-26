package com.retail.ecom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecom.DTO.request.AuthRequest;
import com.retail.ecom.DTO.request.OtpRequest;
import com.retail.ecom.DTO.request.UserRequest;
import com.retail.ecom.DTO.response.AuthResponse;
import com.retail.ecom.DTO.response.UserResponse;
import com.retail.ecom.jwt.JWTService;
import com.retail.ecom.service.UserService;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {
	
	private UserService userService;
	private JWTService jwtService;


	@PostMapping("/register")
	public ResponseEntity<SimpleResponseStructure> registerUser(@RequestBody UserRequest userRequest){
		return userService.registerUser(userRequest);
	}

	@PostMapping("/verify-email")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(@org.springframework.web.bind.annotation.RequestBody OtpRequest otpRequest){
		return userService.verifyOTP(otpRequest);
	}
	@GetMapping("/token")
	public String generateRefreshToken() {
		return jwtService.generateRefreshToken("sakshi hivanse", "CUSTOMER");
	}
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<AuthResponse>> login(@org.springframework.web.bind.annotation.RequestBody AuthRequest authRequest){
		System.err.println("In Controller");
		return userService.loginUser(authRequest);
	}
	@PostMapping("/logout")
    public ResponseEntity<SimpleResponseStructure> logout(@CookieValue(name = "at", required = false) String accessToken,
                                                          @CookieValue(name = "rt", required = false) String refreshToken) {
        return userService.logout(accessToken,refreshToken );
    }
	
}

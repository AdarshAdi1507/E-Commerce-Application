package com.retail.ecom.service;

import org.springframework.http.ResponseEntity;

import com.retail.ecom.DTO.request.AuthRequest;
import com.retail.ecom.DTO.request.OtpRequest;
import com.retail.ecom.DTO.request.UserRequest;
import com.retail.ecom.DTO.response.AuthResponse;
import com.retail.ecom.DTO.response.UserResponse;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

public interface UserService {
public ResponseEntity<SimpleResponseStructure> registerUser(UserRequest userRequest);
public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OtpRequest otpRequest);
public ResponseEntity<ResponseStructure<AuthResponse>> loginUser(AuthRequest authRequest);

public ResponseEntity<SimpleResponseStructure> logout(String accessToken, String refreshToken);

}

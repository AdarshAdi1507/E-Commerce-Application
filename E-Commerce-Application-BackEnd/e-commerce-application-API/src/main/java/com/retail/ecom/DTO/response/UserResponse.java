package com.retail.ecom.DTO.response;

import org.springframework.stereotype.Component;

import com.retail.ecom.DTO.request.AuthRequest;
import com.retail.ecom.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private int userId;
	private String displayName;
	private String userName;
	private String email;
	private Roles userRole;
	private boolean isEmailVerified;
}

package com.retail.ecom.DTO.response;

import com.retail.ecom.enums.Roles;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class AuthResponse {
private int userId;
private String userName;
private String email;
private Roles roles;
private long accessTokenExpiration;
private long refreshTokenEXpiration;

}

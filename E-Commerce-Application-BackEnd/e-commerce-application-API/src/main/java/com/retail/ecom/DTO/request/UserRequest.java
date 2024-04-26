package com.retail.ecom.DTO.request;

import org.springframework.stereotype.Component;

import com.retail.ecom.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserRequest {
private String displayName;
private String email;
private Roles roles;
private String password;
}

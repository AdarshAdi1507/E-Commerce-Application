package com.retail.ecom.model;

import java.util.List;

import com.retail.ecom.enums.Roles;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user table")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String displayName;
	private String userName;
	private String email;
	private String password;
	private Roles userRole;
	private Boolean isEmailVerified;
	private Boolean isDeleted;
	
	@OneToMany
	private List<AccessToken> accessToken;
	@OneToMany
	private List<RefreshToken> refreshToken;
}

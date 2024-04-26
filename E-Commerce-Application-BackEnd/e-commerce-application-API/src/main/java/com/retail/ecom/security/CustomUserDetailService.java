package com.retail.ecom.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.retail.ecom.repository.UserRepository;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
	
  private UserRepository userRepo;
  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUserName(username).map(user-> new CustomUserDetails(user)).orElseThrow(()-> new RuntimeException());
		// map(CustomUSerDetails:: new )
	}
}

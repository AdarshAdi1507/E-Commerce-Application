package com.retail.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecom.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{

	 boolean existsByTokenAndIsBlocked(String refreshToken, boolean b);

	Optional<RefreshToken> findByToken(String refreshToken);

}

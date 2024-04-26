package com.retail.ecom.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.retail.ecom.repository.AccessTokenRepository;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

	private AccessTokenRepository accessTokenRepository;

	private JWTService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();

		System.out.println("In Filter");

		String accessToken = null;
		String refreshToken = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("at")) accessToken = cookie.getValue();
				else if (cookie.getName().equals("rt")) refreshToken = cookie.getValue();
			}
		}
		if (accessToken != null && !accessTokenRepository.existsByTokenAndIsBlocked(accessToken, true)) {
            if (jwtService.getUserName(accessToken) != null) {
                String username = jwtService.getUserName(accessToken);
                String userRole = jwtService.getUserRole(refreshToken); // Corrected to pass token
                Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(userRole));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
		filterChain.doFilter(request, response);
	}
}

package com.retail.ecom.serviceImpl;

import java.time.Duration;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.retail.ecom.DTO.request.AuthRequest;
import com.retail.ecom.DTO.request.OtpRequest;
import com.retail.ecom.DTO.request.UserRequest;
import com.retail.ecom.DTO.response.AuthResponse;
import com.retail.ecom.DTO.response.UserResponse;
import com.retail.ecom.cache.CacheStore;
import com.retail.ecom.enums.Roles;
import com.retail.ecom.exceptions.InvalidUserCredentials;
import com.retail.ecom.exceptions.OTPdoesnotmatch;
import com.retail.ecom.exceptions.OtpExpiredException;
import com.retail.ecom.exceptions.RoleNotSpecifiedException;
import com.retail.ecom.exceptions.SessionExpiredException;
import com.retail.ecom.exceptions.UserAlreadyExistsException;
import com.retail.ecom.exceptions.UserNotLoggedInException;
import com.retail.ecom.jwt.JWTService;
import com.retail.ecom.mail_service.MailService;
import com.retail.ecom.mail_service.MessageModel;
import com.retail.ecom.model.AccessToken;
import com.retail.ecom.model.Customer;
import com.retail.ecom.model.RefreshToken;
import com.retail.ecom.model.Seller;
import com.retail.ecom.model.User;
import com.retail.ecom.repository.AccessTokenRepository;
import com.retail.ecom.repository.RefreshTokenRepository;
import com.retail.ecom.repository.UserRepository;
import com.retail.ecom.service.UserService;
import com.retail.ecom.utility.CookieManager;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
@Service
public class UserServiceImplementation implements UserService{
	private UserRepository userRepository;
	private ResponseStructure<UserResponse> responseStructure;
	private ResponseStructure<AuthResponse> responseStructure1;
	private CacheStore<User> userCacheStore;
	private CacheStore<String> otpCache;
	private SimpleResponseStructure simpleResponseStructure;
	private MailService mailService;
	private AuthenticationManager  authenticationManager;
	private JWTService jwtService;
	private CookieManager cookieManager;

	@Value("${myapp.jwt.refresh.expiration}")
	private long refreshExpiration;
	@Value("${myapp.jwt.access.expiration}")
	private long accessExpiration;
	
	private AccessTokenRepository accessTokenRepository;
	private RefreshTokenRepository refreshTokenRepository;


	public UserServiceImplementation(UserRepository userRepository, ResponseStructure<UserResponse> responseStructure,
			ResponseStructure<AuthResponse> responseStructure1, CacheStore<User> userCacheStore,
			CacheStore<String> otpCache, SimpleResponseStructure simpleResponseStructure, MailService mailService,
			AuthenticationManager authenticationManager, JWTService jwtService, CookieManager cookieManager,
			AccessTokenRepository accessTokenRepository, RefreshTokenRepository refreshTokenRepository) {
		super();
		this.userRepository = userRepository;
		this.responseStructure = responseStructure;
		this.responseStructure1 = responseStructure1;
		this.userCacheStore = userCacheStore;
		this.otpCache = otpCache;
		this.simpleResponseStructure = simpleResponseStructure;
		this.mailService = mailService;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.cookieManager = cookieManager;
		this.accessTokenRepository = accessTokenRepository;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	@Override
	public ResponseEntity<SimpleResponseStructure> registerUser(UserRequest userRequest) {
		if(userRepository.existsByEmail(userRequest.getEmail()))
			throw new UserAlreadyExistsException("User with this id already exists");
		User user = mapToChildEntity(userRequest);
		String otp = generateOTP();
		otpCache.add(userRequest.getEmail(), otp);
		userCacheStore.add(userRequest.getEmail(), user);
		System.err.println(otp);

		try {
			sendOTP(user, otp);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		//return user data


		return ResponseEntity.ok(simpleResponseStructure.setStatus(HttpStatus.ACCEPTED.value()).setMessage("Verify OTP sent "
				+ "throught mail to complete registertaion , OTP expires in 1 minute"));
	}

	private void sendOTP(User user, String otp) throws MessagingException {
		String imageUrl = "https://yourhostedimageurl.com/email-banner.jpg"; 
		MessageModel model = MessageModel.builder().to(user.getEmail())
				.subject("Verify Your Email Address")
				.text("<html><body style=\"font-family: Arial, sans-serif; color: #333;\">"
						+ "<div style=\"text-align: center; padding: 20px;\">"
						+ "<img src=\"" + imageUrl + "\" alt=\"Welcome to Our E-commerce\" style=\"width: 100%; max-width: 600px; height: auto; border: none;\">"
						+ "<h1 style=\"color: #5D6975;\">Verify Your Email Address</h1>"
						+ "<p>Hi " + user.getDisplayName() + ",</p>"
						+ "<p>Thank you for your interest in our E-commerce platform. Please verify your email address using the OTP provided below:</p>"
						+ "<div style=\"background-color: #F9F9F9; margin: 20px 0; padding: 10px; font-size: 20px; font-weight: bold;\">"
						+ "OTP: " + otp
						+ "</div>"
						+ "<p>This OTP is valid for a limited time. If you did not request this OTP, please ignore this email.</p>"
						+ "<p>Regards,<br>Your E-commerce Team</p>"
						+ "</div>"
						+ "</body></html>"
						).build();

		mailService.sendMailMessage(model);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OtpRequest otpRequest) {
		if(otpCache.get(otpRequest.getEmail())==null) throw new OtpExpiredException("otp expired");

		if(!otpCache.get(otpRequest.getEmail()).equals(otpRequest.getOtp())) throw new OTPdoesnotmatch("otp doesnot match");


		User user = userCacheStore.get(otpRequest.getEmail());
		if(user ==null) throw new SessionExpiredException("session expired");
		user.setIsEmailVerified(true);
		user = userRepository.save(user);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(responseStructure.setBody(mapToUserResponse(user))
						.setMessage("OTP verification successfull")
						.setStatuscode(HttpStatus.CREATED.value()));
	}

	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder()
				.userId(user.getUserId())
				.userName(user.getUserName())
				.displayName(user.getDisplayName())
				.email(user.getEmail())
				.isEmailVerified(user.getIsEmailVerified())
				.build();
	}

	private String generateOTP() {
		return String.valueOf(new Random().nextInt(100000, 999999));
	}


	private <T extends User> T mapToChildEntity(UserRequest userRequest) {
		Roles roles = userRequest.getRoles();
		User user = null;
		switch (roles) {
		case SELLER -> user = new Seller();
		case CUSTOMER-> user = new Customer();
		default -> throw new RoleNotSpecifiedException("Invalid role specified: " + roles);
		}

		user.setDisplayName(userRequest.getDisplayName());
		user.setUserName(userRequest.getEmail().split("@gmail.com")[0]);
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setUserRole(userRequest.getRoles());
		user.setIsEmailVerified(false);

		return (T) user;
		
	}

	@Override
	public ResponseEntity<ResponseStructure<AuthResponse>> loginUser(AuthRequest authRequest) {
		String username = authRequest.getUserName().split("@gmail.com")[0];
		System.err.println(username);
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authRequest.getPassword()));
		if(!authentication.isAuthenticated()) throw new InvalidUserCredentials("the user is not authenticated...!");
		SecurityContextHolder.getContext().setAuthentication(authentication);

		HttpHeaders headers = new HttpHeaders();
		User[] user1 = new User[1];
		userRepository.findByUserName(username).ifPresent(user -> {
			user1[0] = user;
			getAccessToken(user , headers);
			getRefreshToken(user, headers);

		});
		
		return ResponseEntity.ok().headers(headers).body(responseStructure1.setStatuscode(HttpStatus.OK.value()).setBody(maptoAuthResponse(user1[0])));

	}

	private void getAccessToken(User  user, HttpHeaders headers) {
		String accessToken = jwtService.generateAccessToken(user.getUserName() , user.getUserRole().name());
		headers.add(HttpHeaders.SET_COOKIE, configureCookie("at", accessToken , accessExpiration));
		AccessToken token = new AccessToken();
		token.setToken(accessToken);
		token.setExpiration(accessExpiration);
		token.setBlocked(false);
		accessTokenRepository.save(token);
		user.getAccessToken().add(token);
		userRepository.save(user);
		//store the token to the database.
		

	}

	private String configureCookie(String name, String value, long maxAge) {
		return ResponseCookie.from(name, value)
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(false)
				.maxAge(Duration.ofMillis(maxAge))
				.sameSite("Lax")
				//there are three type of data we can pass the data into same site
						//"None"-->accepts cookies only from https from only orgin
						//"Lax"-->
						//"Strict"-->accepts cookie only from same origin
				.build().toString();
	}

	private void getRefreshToken(User user, HttpHeaders headers) {
		String refreshToken = jwtService.generateRefreshToken(user.getUserName() , user.getUserRole().name());
		headers.add(HttpHeaders.SET_COOKIE, configureCookie("rt", refreshToken , accessExpiration));
		RefreshToken token = new RefreshToken();
		token.setToken(refreshToken);
		token.setBlocked(false);
		token.setExpiration(refreshExpiration);
		refreshTokenRepository.save(token);
		user.getRefreshToken().add(token);
		userRepository.save(user);
	}
	


	private AuthResponse maptoAuthResponse(User user) {
		return AuthResponse.builder()
				.userId(user.getUserId())
				.userName(user.getUserName())
				.email(user.getEmail())
				.roles(user.getUserRole())
				.accessTokenExpiration(accessExpiration)
				.refreshTokenEXpiration(refreshExpiration)
				.build();
				
	}
	@Override
	public ResponseEntity<SimpleResponseStructure> logout(String accessToken, String refreshToken) {
		System.out.println(accessToken);
		System.out.println(refreshToken);
		 HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.SET_COOKIE, cookieManager.invalidate("at"));
	        headers.add(HttpHeaders.SET_COOKIE, cookieManager.invalidate("rt"));
	        // blocking the tokens
	        blockAccessToken(accessToken);
	        blockRefreshToken(refreshToken);
	   return ResponseEntity.ok().headers(headers).body(simpleResponseStructure.setStatus(HttpStatus.OK.value())
	                .setMessage("Logout Successful"));
		
	}
	private void blockRefreshToken(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).ifPresent(rt -> {
            rt.setBlocked(true);
            refreshTokenRepository.save(rt);
        });
    }
	private void blockAccessToken(String accessToken) {
        accessTokenRepository.findByToken(accessToken).ifPresent(at -> {
            at.setBlocked(true);
            accessTokenRepository.save(at);
        });
    }}
package io.agaileintelligence.ppmtool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.agaileintelligence.ppmtool.entity.User;
import io.agaileintelligence.ppmtool.payload.JWTLoginSucessReponse;
import io.agaileintelligence.ppmtool.payload.LoginRequest;
import io.agaileintelligence.ppmtool.security.JwtTokenProvider;
import io.agaileintelligence.ppmtool.service.MapValidationErrorService;
import io.agaileintelligence.ppmtool.service.UserService;
import io.agaileintelligence.ppmtool.validator.UserValidator;

import static io.agaileintelligence.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired // token provide
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
		if (errorMap != null)
			return errorMap;

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
		);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication); 
		
		return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
//		Validate password match
		userValidator.validate(user, result);

		ResponseEntity<?> errorMapEntity = mapValidationErrorService.MapValidationService(result);

		if (errorMapEntity != null) {
			return errorMapEntity;
		}

		user.setConfirmPassword("");
		User newUser = userService.saveUser(user);

		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}

}

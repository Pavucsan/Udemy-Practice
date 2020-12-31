package io.agaileintelligence.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.agaileintelligence.ppmtool.entity.User;
import io.agaileintelligence.ppmtool.exception.UsernameAlreadyExistException;
import io.agaileintelligence.ppmtool.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUser(User newUser) {


		try {

			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

//			Username has to be unique(exception)
			newUser.setUsername(newUser.getUsername());

//			make sure password and confirm password match
//			we don't persist or show the confirmPassword
			return userRepository.save(newUser);
		} catch (Exception e) {
			throw new UsernameAlreadyExistException("Username " + newUser.getUsername()+" already exist");
		}

	}

}

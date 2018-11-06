package com.apap.tutorial6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial6.model.UserRoleModel;
import com.apap.tutorial6.repository.UserRoleDb;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleDb userDb;
	
	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}

	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
	@Override
	public UserRoleModel getByUsername(String username) {
		return userDb.findByUsername(username);
	}
	
	@Override
	public Boolean checkUserPassword(String username, String password) {
		UserRoleModel user = userDb.findByUsername(username);
		PasswordEncoder token = new BCryptPasswordEncoder();
		Boolean output = token.matches(password, user.getPassword());
		
		return output;
	}
	
	@Override 
	public UserRoleModel updateUserPassword(String username, String password) {
		String pass = encrypt(password);
		UserRoleModel user = userDb.findByUsername(username);
		System.out.println(pass);
		System.out.println(user.getPassword());
		user.setPassword(pass);
		System.out.println(user.getPassword());
		return userDb.save(user);
	}
}
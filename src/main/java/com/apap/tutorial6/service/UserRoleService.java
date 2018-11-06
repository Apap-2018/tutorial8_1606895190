package com.apap.tutorial6.service;

import com.apap.tutorial6.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	public String encrypt(String password);
	UserRoleModel getByUsername(String username);
	Boolean checkUserPassword(String username, String password);
	UserRoleModel updateUserPassword(String username, String password);
}

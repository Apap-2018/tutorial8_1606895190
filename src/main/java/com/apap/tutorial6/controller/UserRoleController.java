package com.apap.tutorial6.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial6.model.UserRoleModel;
import com.apap.tutorial6.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user) {
		userService.addUser(user);
		return "home";
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
	private String updatePasswordUser() {
		return "updatePassword";
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	private String updatePasswordUser(Principal principal, Model model, HttpServletRequest req) {
		String oldPassword =  req.getParameter("oldPassword"); 
		String newPassword =  req.getParameter("password"); 
		String notification = null;
		boolean passwordCorrect = userService.checkUserPassword(principal.getName(), oldPassword);
		boolean oldAndNewCompare = userService.checkUserPassword(principal.getName(), newPassword);
		if (passwordCorrect && !oldAndNewCompare) {
			notification = "Update Password Berhasil.";
			model.addAttribute("notification", notification);
			userService.updateUserPassword(principal.getName(), newPassword);
			return "home";
		}
		else if (oldAndNewCompare){
			notification = "Password baru tidak harus berbeda dari password lama";
			model.addAttribute("notification", notification);
			return "updatePassword";
		}
		else {
			notification = "Password lama yang anda masukkan salah";
			model.addAttribute("notification", notification);
			return "updatePassword";
		}
	}
}
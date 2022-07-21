package com.application.shoeApp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.application.shoeApp.global.GlobalData;
import com.application.shoeApp.model.Role;
import com.application.shoeApp.model.User;
import com.application.shoeApp.repository.RoleRepository;
import com.application.shoeApp.repository.UserRepository;

@Controller
public class LoginController {
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/login")
	public String getLogin(Model model, HttpServletRequest request) {
		GlobalData.cart.clear();
		return "login";
	}
	@GetMapping("/register")
	public String getRegister(HttpServletRequest request) {
		return"register";
	}

	@PostMapping("/register")
	public String postRegister(@ModelAttribute("user") User user, HttpServletRequest request, Model model) throws ServletException {
		String password = user.getPassword();
		System.out.println(bcryptEncoder.encode(password));
		user.setPassword(bcryptEncoder.encode(password));
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepository.findById(2).get());
		user.setRoles(roles);
		userRepository.save(user);
		request.login(user.getEmail(), password);
		return "redirect:/";
	}

}

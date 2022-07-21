package com.application.shoeApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.application.shoeApp.model.CustomUserDetails;
import com.application.shoeApp.model.User;
import com.application.shoeApp.repository.UserRepository;



@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findUserByEmail(username);
		user.orElseThrow(()-> new UsernameNotFoundException("No user found"));
		return user.map(CustomUserDetails::new).get();
		
	}
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	public Optional<User> getUserByEmail(String email){
		return userRepository.findUserByEmail(email);
	}
	
	
}

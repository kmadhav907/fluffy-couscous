package com.application.shoeApp.dto;

import lombok.Data;

@Data
public class UserDTO {
	private String name;
	private String roles;
	private String email;
	public UserDTO(String name, String roles, String email) {
		super();
		this.name = name;
		this.roles = roles;
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

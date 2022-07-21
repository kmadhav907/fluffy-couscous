package com.application.shoeApp.global;

import java.util.ArrayList;
import java.util.List;

import com.application.shoeApp.model.Product;

public class GlobalData {
	public static List<Product> cart = new ArrayList<Product>();
	private static String email;
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		GlobalData.email = email;
	}
	
	
}

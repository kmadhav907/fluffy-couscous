package com.application.shoeApp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.application.shoeApp.dto.ProductDTO;
import com.application.shoeApp.dto.UserDTO;
import com.application.shoeApp.global.GlobalData;
import com.application.shoeApp.model.Category;

import com.application.shoeApp.model.Product;
import com.application.shoeApp.model.Role;
import com.application.shoeApp.model.User;
import com.application.shoeApp.repository.UserRepository;
import com.application.shoeApp.services.CategoryService;
import com.application.shoeApp.services.CustomUserDetailsService;
import com.application.shoeApp.services.ProductService;

@Controller
public class AdminController {

	private static final String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	ProductService productService;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	CustomUserDetailsService userService;

	@GetMapping("/admin")
	public String adminHome() {
//		System.out.println(categoryService.getAllCategory());
		return "adminHome";
	}

	@GetMapping("/admin/categories")
	public String adminCategoriesPage(Model model) {

		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}

	@GetMapping("/admin/categories/add")
	public String getAdminAddCategory(Model model, Category category) {
		model.addAttribute("category", category);
		return "categoriesAdd";
	}

	@PostMapping("/admin/categories/add")
	public String postAdminAddCategory(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable Integer id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/update/{id}")
	public String updateCategory(@PathVariable Integer id, Model model) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "categoriesAdd";
		} else {
			return "404";
		}
	}

	// Product Section

	@GetMapping("/admin/products")
	public String getAllProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}

	@GetMapping("/admin/products/add")
	public String addProductGet(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";
	}

	@PostMapping("/admin/products/add")
	public String addProductPost(@ModelAttribute("productDTO") ProductDTO productDto,
			@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imageName)
			throws IOException {
		Product product = new Product();
		product.setName(productDto.getName());
		product.setId(productDto.getId());
		product.setPrice(productDto.getPrice());
		product.setDescription(productDto.getDescription());
		product.setCategory(categoryService.getCategoryById(productDto.getCategoryId()).get());
		System.out.println(imageName);
		String imageUUID;
		if (!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNamePath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNamePath, file.getBytes());
		} else {
			imageUUID = imageName;

		}
		product.setImageName(imageUUID);
		productService.addProduct(product);
		return "redirect:/admin/products";
	}

	@GetMapping("/admin/product/delete/{id}")
	public String deleteProductById(@PathVariable Long id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}

	@GetMapping("/admin/product/update/{id}")
	public String updateProductById(@PathVariable Long id, Model model) {
		Product product = productService.getProductById(id).get();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());
		productDTO.setImageName(product.getImageName());

		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("productDTO", productDTO);
		return "productsAdd";
	}

	@GetMapping("/admin/users")
	public String showAllUsers(Model model) {
		List<User> users = userService.getAllUsers();
		List<UserDTO> userDtos = new ArrayList<UserDTO>();

		users.forEach(user -> {
			List<Role> role = user.getRoles();
			String roleString = "";
			for (int i = 0; i < role.size(); i++) {

				roleString += role.get(i).getName() + " ";
			}
			UserDTO userDto = new UserDTO(user.getFirstName() + " " + user.getLastName(), roleString, user.getEmail());
			userDtos.add(userDto);
		});
		model.addAttribute("users", userDtos);
		return "users";
	}

	@GetMapping("/forgotpassword")
	public String showForgotPassword(User user) {
		return "forgotpassword";
	}

	@PostMapping("/forgotpassword")
	public String processForgotPassword(User user, Model model) {
		Optional<User> userFromDB = userService.getUserByEmail(user.getEmail());
		if (userFromDB.isPresent()) {
			User existUser = userFromDB.get();
			List<Role> roleOfUser = existUser.getRoles();
			String roleString = "";
			for (int i = 0; i < roleOfUser.size(); i++) {

				roleString += roleOfUser.get(i).getName() + " ";
			}
			if (roleString.contains("ADMIN")) {
				existUser.setPassword("");
				GlobalData.setEmail(existUser.getEmail());
				model.addAttribute("user", existUser);
				return "redirect:/reenterpassword";
			}
			else {
				return "redirect:/forgotpassword?error=true";
			}
		} else {
			return "redirect:/forgotpassword?error=true";
		}

	}
	@GetMapping("/reenterpassword")
	public String reEnterPassword(User user) {
		return "reenterpassword";
	}
	
	@PostMapping("/reenterpassword")
	public String postReEnterPassword(@ModelAttribute("user")User user, HttpServletRequest request) throws ServletException {
	
		Optional<User> userFromDB = userService.getUserByEmail(GlobalData.getEmail());
		if(userFromDB.isPresent()) {
			User existUser = userFromDB.get();
			String password = bcryptEncoder.encode(user.getPassword());
			existUser.setPassword(password);
			System.out.println(existUser.getId());
			userRepository.save(existUser);
			System.out.println(existUser.getId());
			
			return "redirect:/login";
		}
		else {
		
		return "redirect:/login";
		}
	}
}

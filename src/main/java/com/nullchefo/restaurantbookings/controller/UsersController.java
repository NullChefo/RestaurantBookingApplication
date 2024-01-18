package com.nullchefo.restaurantbookings.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.restaurantbookings.dto.UserPasswordChangeDTO;
import com.nullchefo.restaurantbookings.dto.UserRegistrationDTO;
import com.nullchefo.restaurantbookings.dto.retrieve.UserRetrieveDTO;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.service.UsersService;
import com.nullchefo.restaurantbookings.utils.MapperUtility;

import jakarta.validation.Valid;

@RestController()
@RequestMapping(path = "/user")
public class UsersController {

//	private final UsersService userService;
//	private final MapperUtility mapperUtility;
//
//
//
//	public UsersController(UsersService usersService, MapperUtility mapperUtility) {
//		this.userService = usersService;
//		this.mapperUtility = mapperUtility;
//	}
//
//	//	@SecurityRequirement(name = "Bearer Authentication")
//	@GetMapping("/currentUser")
//	public UserRetrieveDTO getCurrentUser() {
//		UserRetrieveDTO userRetrieveDTO = new UserRetrieveDTO();
//
////		Optional<User>	optionalUser = this.userService.getCurrentUser();
////
////		if (optionalUser.isPresent()) {
////			mapperUtility.copyNonNullProps(optionalUser.get(), userRetrieveDTO);
////		}else {
////			userRetrieveDTO = null;
////		}
//		return userRetrieveDTO;
//
//	}
//
//////	@SecurityRequirement(name = "Bearer Authentication")
////	@GetMapping
////	public EditUserDTO getUser() {
////		EditUserDTO editUserDTO;
////
////			User user = this.userService.getUser();
////
////
////			return editUserDTO;
////
////	}
////
//////	@SecurityRequirement(name = "Bearer Authentication")
////	@GetMapping("/profile")
////	public ProfileRetrieveDTO getCurrentProfile() {
////		ProfileRetrieveDTO profileRetrieveDTO;
////
////		User user = this.userService.getCurrentProfile();
////
////		return profileRetrieveDTO;
////	}
////
////
//////	@SecurityRequirement(name = "Bearer Authentication")
////	@GetMapping("/profile/{userId}")
////	public ProfileRetrieveDTO getProfileId(@PathVariable final Long userId) {
////		ProfileRetrieveDTO profileRetrieveDTO;
////
////		User user = this.userService.getProfileById(userId);
////
////
////		return profileRetrieveDTO;
////
////	}
////
//////	@SecurityRequirement(name = "Bearer Authentication")
////	@GetMapping("/profile/username/{username}")
////	public ProfileRetrieveDTO getProfileByUsername(@PathVariable final String username) {
////		ProfileRetrieveDTO profileRetrieveDTO;
////
////		User users = this.userService.getProfileByUsername(username);
////
////		return profileRetrieveDTO;
////
////	}
////
//////	@SecurityRequirement(name = "Bearer Authentication")
////	@PostMapping
////	public EditUserDTO updateUser(@RequestBody final EditUserDTO editUserRetrievalDTO) {
////
////
////		userDTO = this.userService.update(editUserRetrievalDTO);
////
////			return userDTO;
////
////	}
//
////	@SecurityRequirement(name = "Bearer Authentication")
//	@DeleteMapping("/{userId}")
//	public void deleteUser(@PathVariable final UUID userId) {
//			this.userService.delete(userId);
//	}
//
//	@PostMapping("/register")
//	public void registerUser(@RequestBody @Valid UserRegistrationDTO userRegisterDTO) {
//
//		this.userService.createUser(userRegisterDTO);
//
//	}
//
//	@GetMapping("/verifyRegistration")
//	public void verifyRegistration(@RequestParam("token") String token) {
//			userService.validateVerificationToken(token);
//	}
//
//	@GetMapping("/resendVerifyToken")
//	public void resendVerificationToken(@RequestParam("token") String oldToken) {
//		userService.resendVerificationToken(oldToken);
//	}
//
//	@PostMapping("/resetPassword")
//	public void resetPassword(@RequestBody UserPasswordChangeDTO passwordModel) {
//		userService.resetPassword(passwordModel);
//	}
//
//	@PostMapping("/savePassword")
//	public void savePassword(
//			@RequestParam("token") String token,
//			@RequestBody UserPasswordChangeDTO passwordChangeDTO) {
//			userService.savePassword(token, passwordChangeDTO);
//	}
//
//	@PostMapping("/changePassword")
//	public void changePassword(@RequestBody UserPasswordChangeDTO passwordChangeDTO) {
//		userService.changePassword(passwordChangeDTO);
//	}
//









































}

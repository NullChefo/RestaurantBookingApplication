/*
 * Copyright 2024 Stefan Kehayov
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software, or any portion of it, is strictly prohibited.
 *
 * The software is provided "as is", without warranty of any kind,
 * express or implied, including but not limited to the warranties
 * of merchantability, fitness for a particular purpose, and noninfringement.
 * In no event shall the authors or copyright holders be liable for any claim,
 * damages, or other liability, whether in an action of contract, tort, or otherwise,
 * arising from, out of, or in connection with the software or the use or other dealings
 * in the software.
 *
 * Usage of this software by corporations, for machine learning, or AI purposes
 * is expressly prohibited.
 */
package com.nullchefo.restaurantbookings.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/user")
public class UserController {

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

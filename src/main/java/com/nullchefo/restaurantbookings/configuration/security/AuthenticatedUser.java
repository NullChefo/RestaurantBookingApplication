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
package com.nullchefo.restaurantbookings.configuration.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.repository.UserRepository;
import com.vaadin.flow.spring.security.AuthenticationContext;

@Component
public class AuthenticatedUser {

	private final UserRepository userRepository;
	private final AuthenticationContext authenticationContext;

	public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository) {
		this.userRepository = userRepository;
		this.authenticationContext = authenticationContext;
	}

	@Transactional
	public Optional<User> get() {
		return authenticationContext.getAuthenticatedUser(UserDetails.class)
									.map(userDetails -> userRepository
											.findByUsername(userDetails.getUsername())
											.orElse(null));
	}

	public void logout() {
		authenticationContext.logout();
	}

}

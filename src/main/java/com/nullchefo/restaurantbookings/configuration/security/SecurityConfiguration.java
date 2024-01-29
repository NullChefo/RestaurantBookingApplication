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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nullchefo.restaurantbookings.views.auth.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(
				authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll());

		// Icons from the line-awesome addon
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(new AntPathRequestMatcher("/line-awesome/**/*.svg")).permitAll());

		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll());

		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(new AntPathRequestMatcher("/user/**")).permitAll());

		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(new AntPathRequestMatcher("/api-docs/**")).permitAll());

		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(new AntPathRequestMatcher("/v1/api-docs/**")).permitAll());

		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll());

		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll());

		super.configure(http);
		setLoginView(http, LoginView.class);
	}

}

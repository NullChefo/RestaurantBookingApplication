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

package com.nullchefo.restaurantbookings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringNoSecurity {
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//		return httpSecurity
//				.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(auth -> auth
//											   .requestMatchers("/h2-console/*").permitAll()
//											   .anyRequest().authenticated()
//
//									  )
////				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//				.httpBasic(Customizer.withDefaults())
//				.build();
//	}
}

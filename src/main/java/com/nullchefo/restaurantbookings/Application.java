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
package com.nullchefo.restaurantbookings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
//@EnableJpaRepositories
@SpringBootApplication
@Theme(value = "restaurant-bookings")
@Push
public class Application implements AppShellConfigurator {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

	//	@Bean
	//	public ModelMapper modelMapper() {
	//		return new ModelMapper();
	//	}

	//	    @Bean
	//		SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(
	//				DataSource dataSource,
	//				SqlInitializationProperties properties, UserRepository repository) {
	//	        // This bean ensures the database is only initialized when empty
	//	        return new SqlDataSourceScriptDatabaseInitializer(dataSource, properties) {
	//	            @Override
	//	            public boolean initializeDatabase() {
	//	                if (repository.count() == 0L) {
	//	                    return super.initializeDatabase();
	//	                }
	//	                return false;
	//	            }
	//	        };
	//	    }

}

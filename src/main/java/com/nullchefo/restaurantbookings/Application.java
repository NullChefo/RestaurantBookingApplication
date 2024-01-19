package com.nullchefo.restaurantbookings;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.context.annotation.Bean;

import com.nullchefo.restaurantbookings.repository.UserRepository;
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

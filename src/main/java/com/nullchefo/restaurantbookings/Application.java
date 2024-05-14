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

import com.nullchefo.restaurantbookings.agents.CookAgent;
import com.nullchefo.restaurantbookings.agents.CustomerAgent;
import com.nullchefo.restaurantbookings.agents.HostessAgent;
import com.nullchefo.restaurantbookings.agents.TestAgent;
import com.nullchefo.restaurantbookings.agents.WaiterAgent;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import jade.core.Profile;

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
		// TODO if it is in server env to set to true
		System.setProperty("java.awt.headless", "false"); //ADD THIS
		createJadeBean();
		SpringApplication.run(Application.class, args);

	}

	private static void createJadeBean() {
		Runtime runtime = Runtime.instance();

		Profile profile = new ProfileImpl();

		profile.setParameter(Profile.MAIN_HOST,"localhost");
		profile.setParameter(Profile.MAIN_PORT, "1098");// the default port 1099

		// TODO if it is in server env to set to false
		profile.setParameter(Profile.GUI, "true");// the GUI of the platform

		AgentContainer mainContainer = runtime.createMainContainer(profile);





		try {
			AgentController cookAgent = mainContainer.createNewAgent("Cook", CookAgent.class.getName(), null);
			AgentController  customerAgent = mainContainer.createNewAgent("Customer", CustomerAgent.class.getName(), null);
			AgentController  hostessAgent = mainContainer.createNewAgent("Hostess", HostessAgent.class.getName(), null);
			AgentController  waiterAgent = mainContainer.createNewAgent("Waiter", WaiterAgent.class.getName(), null);


			cookAgent.start();
			customerAgent.start();
			hostessAgent.start();
			waiterAgent.start();


			AgentController testAgent = mainContainer.createNewAgent("Test", TestAgent.class.getName(), null);
			testAgent.start();



		} catch (StaleProxyException e) {
			System.out.println("Could not instantiete agents");
			throw new RuntimeException(e);
		}


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

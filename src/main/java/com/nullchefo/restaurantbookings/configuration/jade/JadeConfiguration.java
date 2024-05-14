package com.nullchefo.restaurantbookings.configuration.jade;



import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import jade.wrapper.ControllerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class JadeConfiguration {

//	@Bean()
//	public Runtime jadeRuntime() {
//		Profile profile = new ProfileImpl();
//		profile.setParameter(Profile.GUI, "true"); // Enable the JADE GUI
//		return Runtime.instance();
//	}
//
//	@Bean
//	public AgentContainer agentContainer(Runtime jadeRuntime) throws StaleProxyException, ControllerException {
//		Properties mainContainerProperties = new Properties();
//		mainContainerProperties.setProperty(Profile.GUI, "true"); // Enable the JADE GUI
//		Profile profile = new ProfileImpl(mainContainerProperties);
//		AgentContainer container = jadeRuntime.createMainContainer(profile);
//		return container;
//	}
}

package com.nullchefo.restaurantbookings.configuration.jade;

import org.springframework.stereotype.Component;

import com.nullchefo.restaurantbookings.agents.ClientAgent;
import com.nullchefo.restaurantbookings.agents.CookAgent;
import com.nullchefo.restaurantbookings.agents.HostessAgent;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import jakarta.annotation.PostConstruct;

@Component
public class JadeConfiguration {

	private final Runtime runtime = Runtime.instance();
	private final Profile profile = new ProfileImpl();
	private final boolean IS_HEADLESS = false;// make it config
	private AgentContainer MAIN_AGENT_CONTAINER = null;

	@PostConstruct
	public void initJade() {

		profile.setParameter(Profile.MAIN_HOST, "localhost");
		profile.setParameter(Profile.MAIN_PORT, "1098");// the default port 1099

		if (IS_HEADLESS) {
			profile.setParameter(Profile.GUI, "false");
		} else {
			// if not set spring not working
			System.setProperty("java.awt.headless", "false");
			profile.setParameter(Profile.GUI, "true");
		}

		MAIN_AGENT_CONTAINER = runtime.createMainContainer(profile);

		try {

			AgentController cookAgent = MAIN_AGENT_CONTAINER.createNewAgent("Cook", CookAgent.class.getName(), null);
			AgentController customerAgent = MAIN_AGENT_CONTAINER.createNewAgent(
					"Client",
					ClientAgent.class.getName(), null);
			AgentController hostessAgent = MAIN_AGENT_CONTAINER.createNewAgent(
					"Hostess",
					HostessAgent.class.getName(),
					null);

			cookAgent.start();
			customerAgent.start();
			hostessAgent.start();

		} catch (StaleProxyException e) {
			System.out.println("Could not instantiate agents");
			throw new RuntimeException(e);
		}

	}

	public void addAgentToMainContainer(
			final String nickname,
			final String className,
			final Object[] args) {

		AgentController agentController = null;

		if (MAIN_AGENT_CONTAINER == null) {
			throw new RuntimeException("Jade is not initialized");
		}

		try {
			agentController = MAIN_AGENT_CONTAINER.createNewAgent(nickname, className, args);
		} catch (StaleProxyException e) {
			System.out.println("Could not instantiate agents");
			throw new RuntimeException(e);
		}
		if (agentController == null) {
			throw new RuntimeException("Could not instantiate agents");
		}

		startAgent(agentController);
	}

	public void startAgent(final AgentController agentController) {

		if (MAIN_AGENT_CONTAINER == null) {
			throw new RuntimeException("Jade is not initialized");
		}

		try {
			agentController.start();
		} catch (StaleProxyException e) {
			System.out.println("Could not start agents: " + agentController);
			throw new RuntimeException(e);
		}
	}

}

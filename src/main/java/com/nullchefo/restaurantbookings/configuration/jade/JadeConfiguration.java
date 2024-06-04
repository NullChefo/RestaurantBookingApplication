package com.nullchefo.restaurantbookings.configuration.jade;

import com.nullchefo.restaurantbookings.agents.CookAgent;
import com.nullchefo.restaurantbookings.agents.CustomerAgent;
import com.nullchefo.restaurantbookings.agents.HostessAgent;
import com.nullchefo.restaurantbookings.agents.TestAgent;
import com.nullchefo.restaurantbookings.agents.WaiterAgent;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class JadeConfiguration {

	private static final Runtime runtime = Runtime.instance();
	private static final Profile profile = new ProfileImpl();
	private static AgentContainer MAIN_AGENT_CONTAINER = null;

	public static void initJade(boolean isHostHeadless) {

		profile.setParameter(Profile.MAIN_HOST, "localhost");
		profile.setParameter(Profile.MAIN_PORT, "1098");// the default port 1099

		if (isHostHeadless) {
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
					"Customer",
					CustomerAgent.class.getName(),
					null);
			AgentController hostessAgent = MAIN_AGENT_CONTAINER.createNewAgent(
					"Hostess",
					HostessAgent.class.getName(),
					null);
			AgentController waiterAgent = MAIN_AGENT_CONTAINER.createNewAgent(
					"Waiter",
					WaiterAgent.class.getName(),
					null);

			cookAgent.start();
			customerAgent.start();
			hostessAgent.start();
			waiterAgent.start();

			AgentController testAgent = MAIN_AGENT_CONTAINER.createNewAgent("Test", TestAgent.class.getName(), null);
			testAgent.start();

		} catch (StaleProxyException e) {
			System.out.println("Could not instantiate agents");
			throw new RuntimeException(e);
		}

	}

	public static AgentController addAgentToMainContainer(
			final String nickname,
			final String className,
			final Object[] args) {

		if (MAIN_AGENT_CONTAINER == null) {
			throw new RuntimeException("Jade is not initialized");
		}

		try {
			return MAIN_AGENT_CONTAINER.createNewAgent(nickname, className, args);
		} catch (StaleProxyException e) {
			System.out.println("Could not instantiate agents");
			throw new RuntimeException(e);
		}
	}

	public static void startAgent(final AgentController agentController) {

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

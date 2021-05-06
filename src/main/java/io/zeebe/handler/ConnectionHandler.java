package io.zeebe.handler;

import java.util.Map;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;
import io.zeebe.client.api.response.WorkflowInstanceEvent;

/**
 * This class is responsible to connect to Zeebe
 * 
 * @author Mahfuzul Amin
 *
 */
public class ConnectionHandler {

	//Zeebe instance variables
	public static String MESSAGE_CORRELATION_KEY = "orderId";
	public static String NUMBER_OF_ITEMS = "numberOfItems";
	public static String MESSAGE_TIMEOUT = "messageTimeout";
	
	/**
	 * Connect to Zeebe, run a instance and return the client.
	 * 
	 * @param zeebeHost zeebe host
	 * @param zeebePort zeebe port
	 * @param messageTimeOut message timeOut
	 * @param totalItems total items
	 * @return ZeebeClient zeebe client
	 */
	public static ZeebeClient getZeebeClient(final String zeebeHost, final Integer zeebePort, final Map<String, Object> instanceVariables)  {
		
		// Client connection
		final ZeebeClient client = ZeebeClient.newClientBuilder()
				.gatewayAddress(zeebeHost + ":" + String.valueOf(zeebePort))
				.usePlaintext()
				.build();
		
		System.out.println("Connected to Zeebe");
		
		//Deploy the workflow
        final DeploymentEvent deployment = client.newDeployCommand()
                .addResourceFromClasspath("collect-items.bpmn")
                .send()
                .join();

		final int version = deployment.getWorkflows().get(0).getVersion();
		System.out.println("Workflow deployed. Version: " + version);

        // Create workflow instance
        final WorkflowInstanceEvent wfInstance = client.newCreateInstanceCommand()
            .bpmnProcessId("test-packaging")
            .latestVersion()
            .variables(instanceVariables)
            .send()
            .join();

		final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();

		System.out.println("Workflow instance created. Key: " + workflowInstanceKey);
		
		return client;
	}
}

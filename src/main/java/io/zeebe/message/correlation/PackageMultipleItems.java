package io.zeebe.message.correlation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.worker.JobWorker;
import io.zeebe.handler.ConnectionHandler;
import io.zeebe.handler.PackageItemsJobHandler;
import io.zeebe.message.helper.ZeebeMessageHelper;

/**
 * Sample application to correlate two messages on Zeebe. 
 * 
 * @author Mahfuzul Amin
 *
 */
public class PackageMultipleItems {
	
	public static void main(String[] args) {

		Integer orderID = 123456789;
		
		//Instance variables 
		final Map<String, Object> instanceVariables = new HashMap<>();
		instanceVariables.put(ConnectionHandler.MESSAGE_CORRELATION_KEY, orderID);
		instanceVariables.put(ConnectionHandler.NUMBER_OF_ITEMS, 2);
		instanceVariables.put(ConnectionHandler.MESSAGE_TIMEOUT, "PT5M");

		ZeebeClient client = ConnectionHandler.getZeebeClient("127.0.0.1", 26500, instanceVariables);

        //Send message for first item
		ZeebeMessageHelper.publishMessageToZeebe(client, ZeebeMessageHelper.ZEEBE_WORKFLOW_MESSAGE_CORRELATION_NAME, String.valueOf(orderID), 
				Collections.singletonMap(ZeebeMessageHelper.ITEM, "Mobile"));
      
		//Send message for second item to be correlated with first one
		ZeebeMessageHelper.publishMessageToZeebe(client, ZeebeMessageHelper.ZEEBE_WORKFLOW_MESSAGE_CORRELATION_NAME, String.valueOf(orderID), 
				Collections.singletonMap(ZeebeMessageHelper.ITEM, "Mobile Case"));
      
		//Run the package items worker to run the business logic.
		JobWorker packageItemsWorker = client.newWorker()
				.jobType("package-items")
	            .handler(new PackageItemsJobHandler())
	            .open();
	}
}

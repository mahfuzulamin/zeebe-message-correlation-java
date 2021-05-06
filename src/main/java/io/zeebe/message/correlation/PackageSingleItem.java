package io.zeebe.message.correlation;

import java.util.HashMap;
import java.util.Map;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.worker.JobWorker;
import io.zeebe.handler.ConnectionHandler;
import io.zeebe.handler.PackageItemsJobHandler;
import io.zeebe.message.helper.ZeebeMessageHelper;

/**
 * Sample application sends one message to Zeebe. 
 * 
 * @author Mahfuzul Amin
 *
 */
public class PackageSingleItem {
	
	public static void main(String[] args) {

		Integer orderID = 12345678;
		
		//Instance variables 
		final Map<String, Object> instanceVariables = new HashMap<>();
		instanceVariables.put(ConnectionHandler.MESSAGE_CORRELATION_KEY, orderID);
		instanceVariables.put(ConnectionHandler.NUMBER_OF_ITEMS, 1);
		instanceVariables.put(ConnectionHandler.MESSAGE_TIMEOUT, "PT5M");
		
		//Pass data with instance variable as there is only one item
		instanceVariables.put(ZeebeMessageHelper.ITEM, "Mobile");

		ZeebeClient client = ConnectionHandler.getZeebeClient("127.0.0.1", 26500, instanceVariables);

		//Run the package items worker to run the business logic.
		JobWorker packageItemsWorker = client.newWorker()
				.jobType("package-items")
	            .handler(new PackageItemsJobHandler())
	            .open();
	}
}

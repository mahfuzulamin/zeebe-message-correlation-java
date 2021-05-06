package io.zeebe.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;
import io.zeebe.message.helper.ZeebeMessageHelper;

/**
 * This is a helper job handler to package items. 
 * 
 * @author Mahfuzul Amin
 *
 */
public class PackageItemsJobHandler implements JobHandler {
	private static String PACKAGED_ITEM_LIST = "packagedItemList";
	private static String PACKAGED_ITEM_COUNT = "collectedItems";
	
	@Override
	public void handle(final JobClient client, final ActivatedJob job) {
		
		//Get the variables
		final Map<String, Object> variables = job.getVariablesAsMap();
		
		//Get the item and item list and add the new item to the list
		String item = String.valueOf(variables.get(ZeebeMessageHelper.ITEM));
		List<String> itemList = (List) variables.getOrDefault(PACKAGED_ITEM_LIST, new ArrayList<>());
		itemList.add(item);
		
		variables.put(PACKAGED_ITEM_LIST, itemList);
		
		//Increase the item count
		variables.put(PACKAGED_ITEM_COUNT, (Integer) variables.getOrDefault(PACKAGED_ITEM_COUNT, 0) + 1);

		System.out.println("Packaged items: " + Arrays.toString(itemList.toArray()));
        
        client.newCompleteCommand(job.getKey())
        	.variables(variables)
            .send()
            .join();
    }
}
package io.zeebe.message.helper;

import java.util.Map;

import io.zeebe.client.ZeebeClient;

/**
 * Helper class for sending messages to Zeebe.
 * 
 * @author Mahfuzul Amin
 *
 */
public class ZeebeMessageHelper {

	//Zeebe message variables
	public static final String ITEM = "item";
	public static final String  ZEEBE_WORKFLOW_MESSAGE_CORRELATION_NAME = "Collect Items";
    
	/**
     * Publish message to zeebe.
     * @param client zeebe cleint
     * @param zeebeMsgCorrelationName zeebe message correlation name
     * @param uniqueKey uniqueKey
     * @param data data variables
     */
    public static void publishMessageToZeebe(final ZeebeClient client, final String zeebeMsgCorrelationName, final String uniqueKey, final Map<String, Object> data) {
    	System.out.println(String.format("Publishing message to zeebe; zeebeMsgCorrelationName: %s, uniqueKey: %s", zeebeMsgCorrelationName, uniqueKey));

		client
        .newPublishMessageCommand()
        .messageName(zeebeMsgCorrelationName)
        .correlationKey(uniqueKey)
        .variables(data)
        .send()
        .join();
    }
}

# Zeebe Message Correlation Java
This is a Java implementation of message correlation on Zeebe. This project simulates packaging multiple items in a order where order id is the unique key. Message correlation is driven through the packaging job handler of the project. This project does the followings:
* Deploy's collect-items.bpmn BPMN Workflow to Zeebe. 
* Correlates multiple items in a order based on order id which is the unique key.
* For multiple items data is published as message.
* For single item data is sent as instance variable.

### Build project
* mvn clean install

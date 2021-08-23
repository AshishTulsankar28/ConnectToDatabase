# Kafka Connect Suite of JMS Source Connectors

This project provides a suite of connectors that source records from various JMS brokers to Apache Kafka.

## Supported JMS Brokers

* [Generic JMS (configured via JNDI)](https://github.com/confluentinc/kafka-connect-jms/tree/master/kafka-connect-jms)
* [ActiveMQ](https://github.com/confluentinc/kafka-connect-jms/tree/master/kafka-connect-activemq)
* [IBM MQ](https://github.com/confluentinc/kafka-connect-jms/tree/master/kafka-connect-ibmmq)
* TIBCO EMS
* Solace

## Development

The core of this suite is implemented over in the [`kafka-connect-jms`](https://github.com/confluentinc/kafka-connect-jms/tree/master/kafka-connect-jms) module. 

Each connector has to extend a few abstractions to get going -

* `BaseJmsSourceTask`
    * Implement `connectionFactory()`
    * Implement `config()`
* `BaseJmsSourceConnector`
    * Supply a subclass of the `BaseJmsSourceConnectorConfig` 

package com.cwm.kafka.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Component;

import com.cwm.kafka.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaConsumer {

	private static final Logger LOGGER=LoggerFactory.getLogger(KafkaConsumer.class);
	ObjectMapper object=new ObjectMapper();
	 @KafkaListener(topics = "employee-topic", groupId = "employee-id")
	    public void consumeMessage(String message){
	       
		 LOGGER.info("Kafka consumer recived message:: "+message);
		 
	    }
}

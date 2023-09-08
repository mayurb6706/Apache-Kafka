package com.cwm.employee.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaProducer {

	
	@Value("${employee.topic.name}")
	private String kafkaTopic;
	
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	ObjectMapper object=new ObjectMapper();
	
	public void producerMessage(String message) {
		kafkaTemplate.send(this.kafkaTopic,message);
	}
	
	
	public void updatedMessage(String message) {
		kafkaTemplate.send(this.kafkaTopic,message);
	}
}

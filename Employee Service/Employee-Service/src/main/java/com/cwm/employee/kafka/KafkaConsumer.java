package com.cwm.employee.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

	
	@KafkaListener(topics = "${employee.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumeMessage(String message) {
		System.out.println("Message recived from kafka producer==> "+message);
	}
}

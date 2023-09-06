package com.cwm.kafka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@Value("${employee.topic.name}")
	private String topic;


	public void sendMessage(String message) {
		kafkaTemplate.send(topic, message);
	}
}

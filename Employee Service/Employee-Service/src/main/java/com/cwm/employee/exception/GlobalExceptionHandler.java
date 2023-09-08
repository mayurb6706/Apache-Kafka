package com.cwm.employee.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cwm.employee.kafka.KafkaProducer;
import com.cwm.employee.model.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class GlobalExceptionHandler {
	
	ObjectMapper object=new ObjectMapper();
	@Autowired
	KafkaProducer kafkaProducer;
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ErrorMessage handleEmployeeNotFoundException(EmployeeNotFoundException ex) throws JsonProcessingException{
		ErrorMessage em=ErrorMessage.builder()
				.message(ex.getLocalizedMessage())
				.status(HttpStatus.NOT_FOUND)
				.errorCode(HttpStatus.NOT_FOUND.value())
				.build();
		kafkaProducer.producerMessage(object.writeValueAsString(em));
		return em;
	}

}

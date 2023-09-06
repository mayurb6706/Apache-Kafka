package com.cwm.kafka.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.kafka.config.KafkaProducer;
import com.cwm.kafka.model.Employee;
import com.cwm.kafka.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Autowired
	private EmployeeService service;
	
	ObjectMapper object=new ObjectMapper();
	//add new Employee
	
	@PostMapping("/")
	public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee emp){
		
		Employee employee=this.service.addEmployee(emp);
		try {
			String message=this.object.writeValueAsString(employee);
			kafkaProducer.sendMessage(message);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<Employee>(employee,HttpStatus.OK);
	}
	
	//get the Single Employee
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws JsonProcessingException{
		Employee employee=this.service.getEmployee(id);
		String message=object.writeValueAsString(employee);
		kafkaProducer.sendMessage(message);
		return  ResponseEntity.ok(employee);
	}
	
	@DeleteMapping("/")
	public ResponseEntity<String> removeEmployee(@PathVariable Long id){
		this.service.removeEmployee(id);
		return ResponseEntity.ok("Deleted Employee Details Sucessfully!");
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Employee>> getAllEmployee(){
		List<Employee> list=this.service.getAllEmployee();
		try {
		Object message=list.stream()
				.map(emp->{
					return emp;
				}).collect(Collectors.toList());
		
		kafkaProducer.sendMessage(object.writeValueAsString(message));
		}
		catch (Exception e) {
	
		}
		return new ResponseEntity<List<Employee>>(list,HttpStatus.OK);
	}
	
	@GetMapping("/name/{name}")
	public List<Employee> getEmployeeByName(@PathVariable String name){
		return this.service.getAllEmployeeByName(name);
	}
}


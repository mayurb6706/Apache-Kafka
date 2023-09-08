package com.cwm.employee.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.employee.kafka.KafkaProducer;
import com.cwm.employee.model.Employee;
import com.cwm.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	private static final Logger LOGGER=LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	EmployeeService service;
	
	
	ObjectMapper object=new ObjectMapper();
	@Autowired
	private KafkaProducer kafkaProducer;
	@PostMapping("/save")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee emp){
		Employee employee=this.service.saveEmployee(emp);
		try {
			String message=object.writeValueAsString(employee);
			kafkaProducer.producerMessage("Newly added the enity :: "+message);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return ResponseEntity.ok(employee);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable Long id){
		Employee employee=this.service.getEmployeeById(id);
		try {
			kafkaProducer.producerMessage(object.writeValueAsString(employee));
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return ResponseEntity.ok(employee);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Employee>> getALlEmployee(){
		List<Employee> list= this.service.getAllEmployee();
		try {
			Object employeeObject= list.stream()
				.map(employee->{
					return employee;
				}).collect(Collectors.toList());
			String message=object.writeValueAsString(employeeObject);
			kafkaProducer.producerMessage(message);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<List<Employee>>(list,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> removeEmployee(@PathVariable Long id){
		this.service.removeEmployee(id);
		kafkaProducer.producerMessage("Employee details are removed for employee :: "+id);
		return new ResponseEntity<String>("Employee removed!",HttpStatus.OK);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<Employee> updateEmployee(@RequestParam Long id, @RequestBody Employee employee){
		Employee updatedEmployee=this.service.updateEmployee(id, employee);
		
		try {
			String message=this.object.writeValueAsString(updatedEmployee);
			kafkaProducer.updatedMessage("Updated the entity :: "+message);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
	}

}

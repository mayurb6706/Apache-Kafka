package com.cwm.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cwm.employee.exception.EmployeeNotFoundException;
import com.cwm.employee.model.Employee;
import com.cwm.employee.repository.EmployeeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeService {

	
	@Autowired
	EmployeeRepo empRepo;
	
	public Employee saveEmployee(Employee emp) {
		Employee employee =empRepo.save(emp);
		return employee;
	}
	
	
	public Employee getEmployeeById(Long id) {
		Employee employee=this.empRepo.findById(id).
				orElseThrow(()-> new EmployeeNotFoundException("Employee Not Exist"));
		return employee;
	}
	
	public List<Employee> getAllEmployee(){
		return this.empRepo.findAll();
	}


	public void removeEmployee(Long id) {
		Employee emp=this.getEmployeeById(id);
		this.empRepo.delete(emp);
		
	}
	
	public Employee updateEmployee(Long id, Employee emp) {
		Employee employee=this.getEmployeeById(id);
		employee.setName(emp.getName());
		employee.setEmail(emp.getEmail());
		employee.setAddress(emp.getAddress());
		this.empRepo.save(employee);
		return employee;
	}
}

package com.cwm.kafka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwm.kafka.model.Employee;
import com.cwm.kafka.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepo;
	
	public Employee addEmployee(Employee employee)
	{
		Employee emp=this.empRepo.save(employee);
		return emp;
	}

	public Employee getEmployee(Long id) {
		Employee employee=this.empRepo.findById(id).get();
		return employee;
	}
	
	public void removeEmployee(Long id) {
		Employee employee=this.getEmployee(id);
		this.empRepo.delete(employee);
	}

	public List<Employee> getAllEmployee() {
		
		return this.empRepo.findAll();
	}
	
public List<Employee> getAllEmployeeByName(String name) {
		List<Employee> list=this.empRepo.findByNameIsLike(name);
		return list;
	}
	
}

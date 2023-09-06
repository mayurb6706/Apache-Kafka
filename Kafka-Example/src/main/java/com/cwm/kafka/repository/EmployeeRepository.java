package com.cwm.kafka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwm.kafka.model.Employee;
@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long>{

	public List<Employee> findByNameIsLike(String name);
}

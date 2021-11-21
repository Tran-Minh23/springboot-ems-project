package com.springboot.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ems.model.Employee;
import com.springboot.ems.repository.EmployeeRepository;
import com.springboot.ems.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(long id) {
		if (employeeRepository.findById(id).isPresent()) {
			return employeeRepository.findById(id).get();
		}
		return null;
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public List<Employee> findByDepartment_id(int id) {
		return employeeRepository.findByDepartment_id(id);
	}
}

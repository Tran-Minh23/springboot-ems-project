package com.springboot.ems.service;

import java.util.List;

import com.springboot.ems.model.Employee;

public interface EmployeeService {
	Employee saveEmployee(Employee employee);
	List<Employee> getAllEmployees();
	Employee getEmployeeById(long id);
	Employee updateEmployee(Employee employee);
	void deleteEmployee(long id);
	List<Employee> findByDepartment_id(int id);
}

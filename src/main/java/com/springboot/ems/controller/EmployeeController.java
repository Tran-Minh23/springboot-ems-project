package com.springboot.ems.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ems.dto.EmployeeDto;
import com.springboot.ems.model.Department;
import com.springboot.ems.model.Employee;
import com.springboot.ems.service.DepartmentService;
import com.springboot.ems.service.EmployeeService;

@RestController
@CrossOrigin
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@Autowired EmployeeService employeeService;
	
	@Autowired DepartmentService departmentService;
	
	// Create new employee
	@PostMapping
	public ResponseEntity<Map<String, Object>> saveEmployee(@RequestBody EmployeeDto request) {
		// validate data later
		Employee em = new Employee();
		em.setFirstName(request.getFirstName());
		em.setLastName(request.getLastName());
		em.setEmail(request.getEmail());
		em.setGender(request.getGender());
		em.setPhone(request.getPhone());
		em.setAddress(request.getAddress());
		
		Department department = departmentService.getDepartmentById(request.getDepartmentId());
		if(department == null) {
			return new ResponseEntity<>(notFound("department"), HttpStatus.OK);
		}
		
		em.setDepartment(department);
		employeeService.saveEmployee(em);
		LinkedHashMap<String, Object> response= new LinkedHashMap<String, Object>();
		response.put("errorCode", 0);
		response.put("data", em);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	// Get all employees
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllEmployees() {
		ArrayList<Employee> employees = (ArrayList<Employee>) employeeService.getAllEmployees();
		
		LinkedHashMap<String, Object> response= new LinkedHashMap<String, Object>();
		response.put("errorCode", 0);
		response.put("data", employees);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	// find employee by id
	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> getEmployeeById(@PathVariable("id") long id) {
		LinkedHashMap<String, Object> response= new LinkedHashMap<String, Object>();
		Employee employee = employeeService.getEmployeeById(id);
		
		if (employee == null) {
			return new ResponseEntity<>(notFound("employee"), HttpStatus.OK);
		}
		
		response.put("errorCode", 0);
		response.put("data", employee);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	// update employee
	@PutMapping("{id}")
	public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable("id") long id,
																@RequestBody EmployeeDto request) {
		LinkedHashMap<String, Object> response= new LinkedHashMap<String, Object>();
		
		Employee employee = employeeService.getEmployeeById(id);
		if (employee == null) {
			return new ResponseEntity<>(notFound("employee"), HttpStatus.OK);
		}
		
		Department department = departmentService.getDepartmentById(request.getDepartmentId());
		if (department == null) {
			return new ResponseEntity<>(notFound("department"), HttpStatus.OK);
		}
		
		employee.setFirstName(request.getFirstName());
		employee.setLastName(request.getLastName());
		employee.setEmail(request.getEmail());
		employee.setGender(request.getGender());
		employee.setPhone(request.getPhone());
		employee.setAddress(request.getAddress());
		employee.setDepartment(department);
		
		response.put("errorCode", 0);
		response.put("data", employeeService.updateEmployee(employee));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	// delete employee
	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable("id") long id) {
		Employee employee = employeeService.getEmployeeById(id);
		if (employee == null) {
			return new ResponseEntity<>(notFound("employee"), HttpStatus.OK);
		}
		
		employeeService.deleteEmployee(id);
		
		LinkedHashMap<String, Object> response= new LinkedHashMap<String, Object>();
		response.put("errorCode", 0);
		response.put("message", "Delete successful");
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	// get employee by department
	@GetMapping("/depart/{id}")
	public ResponseEntity<Map<String, Object>> getEmployeeByDepartment(@PathVariable("id") int id) {
		LinkedHashMap<String, Object> response= new LinkedHashMap<String, Object>();
		ArrayList<Employee> employees = (ArrayList<Employee>) employeeService.findByDepartment_id(id);
		response.put("errorCode", 0);
		response.put("data", employees);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	// Response when not found employee
	private Map<String, Object> notFound(String data) {
		String message = (data.equalsIgnoreCase("employee")) ? "Employee" : "Department";
		LinkedHashMap<String, Object> error= new LinkedHashMap<String, Object>();
		error.put("errorCode", 1);
		error.put("message", message + " is not found");
		
		return error;
	}
}

package com.springboot.ems.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.ems.dto.DepartmenDto;
import com.springboot.ems.model.Department;
import com.springboot.ems.service.DepartmentService;

@Controller
@CrossOrigin
@RequestMapping("/api/departments")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	
	// Insert new department
	@PostMapping
	public ResponseEntity<Map<String, Object>> saveDepartment(@RequestBody DepartmenDto request) {
		Map<String, Object> response= new LinkedHashMap<String, Object>();
		
		Department department = new Department();
		department.setDepartmentName(request.getDepartmentName());
		
		response.put("errorCode", 0);
		response.put("data", departmentService.saveDepartment(department));
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	// Get all departments
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllDepartments() {
		Map<String, Object> response= new LinkedHashMap<String, Object>();
		response.put("errorCode", 0);
		ArrayList<Department> departments = (ArrayList<Department>) departmentService.getAllDepartments();
		response.put("data", departments);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	// get department by id
	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> getDepartmentById(@PathVariable("id") int id) {
		LinkedHashMap<String, Object> response= new LinkedHashMap<String, Object>();
		Department department = departmentService.getDepartmentById(id);
		
		if (department == null) {
			return new ResponseEntity<>(notFoundDepartment(), HttpStatus.OK);
		}
		
		response.put("errorCode", 0);
		response.put("data", department);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	// update department
	@PutMapping("{id}")
	public ResponseEntity<Map<String, Object>> updateDepartment(@PathVariable("id") int id,
																@RequestBody DepartmenDto request) {
		Department department = departmentService.getDepartmentById(id);
		if (department == null) {
			return new ResponseEntity<>(notFoundDepartment(), HttpStatus.OK);
		}
		
		department.setDepartmentName(request.getDepartmentName());
		LinkedHashMap<String, Object> response= new LinkedHashMap<String, Object>();
		response.put("errorCode", 0);
		response.put("data", departmentService.updateDepartment(department));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	// delete department
	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Object>> deleteDepartment(@PathVariable("id") int id) {
		Department department = departmentService.getDepartmentById(id);
		if (department == null) {
			return new ResponseEntity<>(notFoundDepartment(), HttpStatus.OK);
		}
		
		LinkedHashMap<String, Object> response= new LinkedHashMap<String, Object>();
		response.put("errorCode", 0);
		response.put("message", "Delete successful");
		departmentService.deleteDepartment(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	private Map<String, Object> notFoundDepartment() {
		LinkedHashMap<String, Object> error= new LinkedHashMap<String, Object>();
		error.put("errorCode", 1);
		error.put("message", "Department is not found");
		return error;
	}
}

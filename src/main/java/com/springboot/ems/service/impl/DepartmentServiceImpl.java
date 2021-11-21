package com.springboot.ems.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ems.exception.ResourceNotFoundException;
import com.springboot.ems.model.Department;
import com.springboot.ems.repository.DepartmentRepository;
import com.springboot.ems.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	DepartmentRepository departmentRepository;

	@Override
	public Department saveDepartment(Department department) {
		return departmentRepository.save(department);
	}

	@Override
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	@Override
	public Department getDepartmentById(int id) {
		if (departmentRepository.findById(id).isPresent()) {
			return departmentRepository.findById(id).get();
		}
			return null;
	}

	@Override
	public Department updateDepartment(Department department) {
		return departmentRepository.save(department);
	}

	@Override
	public void deleteDepartment(int id) {
		departmentRepository.deleteById(id);
	}
	
}

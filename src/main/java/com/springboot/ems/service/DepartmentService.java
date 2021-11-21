package com.springboot.ems.service;

import java.util.List;


import com.springboot.ems.model.Department;

public interface DepartmentService {
	Department saveDepartment(Department department);
	List<Department> getAllDepartments();
	Department getDepartmentById(int id);
	Department updateDepartment(Department department);
	void deleteDepartment(int id);
}

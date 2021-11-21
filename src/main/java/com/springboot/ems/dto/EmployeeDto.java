package com.springboot.ems.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.springboot.ems.model.Department;

import lombok.Data;

@Data
public class EmployeeDto {
	private String firstName;
	private String lastName;
	private String gender;
	private String phone;
	private String email;
	private String address;
	private int departmentId;
}

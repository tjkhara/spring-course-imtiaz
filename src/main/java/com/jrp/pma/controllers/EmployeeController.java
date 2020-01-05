package com.jrp.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.entities.Employee;
import com.jrp.pma.entities.Project;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	
	
	@Autowired
	EmployeeRepository employeeRepo;
	
	@GetMapping("/new")
	public String displayEmployeeForm(Model model) {
		
		Employee anEmployee = new Employee();
		model.addAttribute("employee", anEmployee);
		return "employees/new-employee";
	}
	
	@PostMapping("/save")
	public String createEmployee(Employee employee, Model model) {
		
		// this should handle saving to the database
		employeeRepo.save(employee);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/new";
		
	}
	
	@GetMapping
	public String displayEmployees(Model model) {
		
		List<Employee> employees = employeeRepo.findAll();
		model.addAttribute("employees",employees);
		return "employees/list-employees";
	}
	
}

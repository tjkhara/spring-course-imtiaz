package com.jrp.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.dao.ProjectRepository;
import com.jrp.pma.entities.Employee;
import com.jrp.pma.entities.Project;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	ProjectRepository proRepo;

	@Autowired
	EmployeeRepository empRepo;

	@GetMapping("/new")
	public String displayProjectForm(Model model) {

		Project aProject = new Project();

		List<Employee> employees = empRepo.findAll();

		model.addAttribute("project", aProject);
		model.addAttribute("allEmployees", employees);
		return "projects/new-project";
	}

	@PostMapping("/save")
	public String createProject(Project project, @RequestParam List<Long> employees, Model model) {

		// this should handle saving to the database
		proRepo.save(project);

		// the employee table updated with the id of the project
		Iterable<Employee> chosenEmployees = empRepo.findAllById(employees);

		for (Employee emp : chosenEmployees) {
			emp.setProject(project);
			empRepo.save(emp);
		}

		// use a redirect to prevent duplicate submissions
		return "redirect:/projects/new";

	}

	@GetMapping
	public String displayProjects(Model model) {

		List<Project> projects = proRepo.findAll();
		model.addAttribute("projects", projects);
		return "projects/list-projects";
	}

}

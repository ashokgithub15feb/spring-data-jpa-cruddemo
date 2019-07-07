package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	//quick and dirty: inject employee doa
	private EmployeeService employeeService;
	
	public EmployeeRestController(EmployeeService employeeService)
	{
		this.employeeService = employeeService;
	}
	
	//expose "/employees" and return list of employee
	@GetMapping("/employees")
	public List<Employee> findAll()
	{
		return employeeService.findAll();
	}
	
	@GetMapping("/employees/{employeeId}")
	public Employee findById(@PathVariable("employeeId") int employeeId)
	{
		Employee theEmployee = employeeService.findById(employeeId);
		
		if(theEmployee == null)
		{
			throw new RuntimeException("Employee is not found: "+employeeId);
		}
		
		return theEmployee;
	}
	
	//add mapping for post /employee - add new employee
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee)
	{
		theEmployee.setId(0);
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee)
	{
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId)
	{
		Employee tempEmployee = employeeService.findById(employeeId);
		
		//throw if employee is not exist
		
		if(tempEmployee == null)
		{
			throw new RuntimeException("Employee id not found: "+employeeId);
		}
		
		employeeService.deleteById(employeeId);
		
		return "Deleted Employee Id: "+employeeId;
	}
}

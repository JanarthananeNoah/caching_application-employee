package com.caching.employee_cache.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caching.employee_cache.entity.Employee;
import com.caching.employee_cache.service.EmployeeService;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {


	@Autowired private EmployeeService employeeService;
	
	@Cacheable(cacheNames = "employees")
	@GetMapping("/all")
	public ResponseEntity<List<Employee>> getAllEmployee(){
		return ResponseEntity.ok(employeeService.getAllEmployee());
	}
	
	@Cacheable(cacheNames = "employee", key = "#id")
	@GetMapping("/get-emp")
	public ResponseEntity<Employee> getEmployeeById(@RequestParam Integer id){
		return ResponseEntity.ok(employeeService.getEmployeeById(id));
	}
	
	@PostMapping("/add-employee")
	public ResponseEntity<String> addEmployee(Employee employee){
		if(employeeService.addEmployee(employee)) {
			return new ResponseEntity<>("Employee details successfully added", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Employee details successfully added", HttpStatus.BAD_REQUEST);
	}
	
	@CachePut(value = "employees",key = "#up")
	@PutMapping("/edit-employee")
	public ResponseEntity<Employee> updateEmployee(Employee employee,@RequestParam Integer id){
		Employee employee2 = employeeService.updateEmployee(employee, id);
		if(null != employee2) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@CacheEvict(value = "employees",key = "#id")
	@DeleteMapping("/delete-emp")
	public ResponseEntity<Employee> deleteEmployee(@RequestParam Integer id){
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}

}

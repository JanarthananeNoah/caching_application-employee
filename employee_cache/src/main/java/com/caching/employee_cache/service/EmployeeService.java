package com.caching.employee_cache.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caching.employee_cache.entity.Employee;
import com.caching.employee_cache.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(Integer id) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		return optionalEmployee.orElseThrow(() -> null);
	}

	public boolean addEmployee(Employee employee) {
		if (null != employee) {
			employeeRepository.save(employee);
			return true;
		}
		return false;
	}

	public Employee updateEmployee(Employee employee, Integer id) {
		Employee updateEmployee;
		Optional<Employee> exsistingemployee = employeeRepository.findById(id);

		if (exsistingemployee.isPresent()) {
			updateEmployee = exsistingemployee.get();

			if (!updateEmployee.getName().equals(employee.getName())) {
				updateEmployee.setName(employee.getName());
			}

			if (!updateEmployee.getDomain().equals(employee.getDomain())) {
				updateEmployee.setDomain(employee.getDomain());
			}

			if (!updateEmployee.getSalary().equals(employee.getSalary())) {
				updateEmployee.setSalary(employee.getSalary());
			}
			employeeRepository.save(updateEmployee);
			return updateEmployee;
		}
		return employee;
	}
	
	public void deleteEmployee(Integer id) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		optionalEmployee.ifPresent(employee -> employeeRepository.delete(employee));
	}


}

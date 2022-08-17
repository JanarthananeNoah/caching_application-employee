package com.caching.employee_cache.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caching.employee_cache.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

}

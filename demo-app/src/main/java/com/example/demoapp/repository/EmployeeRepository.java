package com.example.demoapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demoapp.model.Employee;


@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

	Employee findByEmail(String email);
	List<Employee> findByDepartmentDptId(Long id);
}

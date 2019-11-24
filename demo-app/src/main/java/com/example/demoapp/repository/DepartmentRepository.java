package com.example.demoapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demoapp.model.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long>{
	
}

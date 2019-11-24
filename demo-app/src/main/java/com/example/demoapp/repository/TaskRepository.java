package com.example.demoapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.demoapp.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
	//List<Task> findByEmployeeEmpId(Long empId);
}

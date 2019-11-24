package com.example.demoapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoapp.model.Employee;
import com.example.demoapp.model.Task;
import com.example.demoapp.repository.TaskRepository;

@RestController
public class TaskController {

	@Autowired
	TaskRepository taskRepository;

	@GetMapping("/tasks")
	public List<Task> getAllTasks() {

		Iterable<Task> it = taskRepository.findAll();
		List tasks = new ArrayList<Task>();
		it.forEach(task -> {
			tasks.add(task);
		});

		return tasks;

	}

	@PostMapping("/tasks")
	public Task saveTask(@Valid @RequestBody Task task) {

		return taskRepository.save(task);

	}

	// To get info for particular taskId
	@GetMapping("/tasks/{taskid}")
	public Task getTask(@PathVariable(value = "taskid") Long taskid) {

		return taskRepository.findById(taskid).orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskid));

	}

	@PutMapping("/tasks/{taskid}")
	public Task updateTask(@Valid @RequestBody Task requestTask, @PathVariable(value = "taskid") Long taskid) {

		Task task = taskRepository.findById(taskid)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskid));
		task.setTaskName(requestTask.getTaskName());
		return taskRepository.save(task);
	}

	@DeleteMapping("/tasks/{taskid}")
	public ResponseEntity<?> deleteTask(@PathVariable(value = "taskid") Long taskid) {

		Task task = taskRepository.findById(taskid)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskid));
		taskRepository.delete(task);
		return ResponseEntity.ok().build();

	}

	@GetMapping("/tasks/{taskid}/employees")
	public Set<Employee> getEmployeesByTask(@PathVariable(value = "taskid") Long taskid) {

		Task task = taskRepository.findById(taskid)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskid));
		return task.getEmployees();

	}
}

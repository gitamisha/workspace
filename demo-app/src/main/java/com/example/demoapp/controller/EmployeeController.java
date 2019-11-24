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
import com.example.demoapp.repository.DepartmentRepository;
import com.example.demoapp.repository.EmployeeRepository;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployee() {

		Iterable<Employee> it = employeeRepository.findAll();
		List employees = new ArrayList<Employee>();
		it.forEach(e -> employees.add(e));
		return employees;

	}

	@PostMapping("/employees/{dptId}/employee")
	public Object saveEmployee(@PathVariable(value = "dptId") Long dptId, @Valid @RequestBody Employee employee) {

		if (employeeRepository.findByEmail(employee.getEmail()) != null) {
			return new String("Email-Id already exist");
		}

		return departmentRepository.findById(dptId).map(dpt -> {
			employee.setDepartment(dpt);
			return employeeRepository.save(employee);
		}).orElseThrow(() -> new ResourceNotFoundException("Department", "id", dptId));
	}

	@GetMapping("/employees/{empId}")
	public Employee getEmployee(@PathVariable(value = "empId") Long empId) {

		return employeeRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", empId));

	}

	@PutMapping("/employees/{empId}")
	public Employee updateEmployee(@PathVariable(value = "empId") Long empId, @Valid @RequestBody Employee employee) {

		if (!employeeRepository.existsById(empId)) {

			throw new ResourceNotFoundException("Employee", "id", empId);

		}

		return employeeRepository.findById(empId).map(emp -> {

			emp.setFirstName(employee.getFirstName());
			emp.setLastName(employee.getLastName());
			emp.setAddress(employee.getAddress());

			return employeeRepository.save(emp);

		}).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", empId));

	}

	@DeleteMapping("/employees/{empId}")
	public ResponseEntity<?> deleteEmployees(@PathVariable(value = "empId") Long empId) {

		Employee employee = employeeRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", empId));
		employeeRepository.delete(employee);
		return ResponseEntity.ok().build();

	}

	@GetMapping("/employees/{dptId}/employee")
	public List<Employee> getAllEmployeeFromDepartment(@PathVariable(value = "dptId") Long dptId) {

		return employeeRepository.findByDepartmentDptId(dptId);

	}

	@DeleteMapping("/employees/{dptId}/employee")
	public ResponseEntity<?> deleteAllEmployeesForGivenDepartment(@PathVariable(value = "dptId") Long dptId) {

		List<Employee> empList = employeeRepository.findByDepartmentDptId(dptId);
		empList.forEach(emp -> {
			employeeRepository.delete(emp);
		});

		return ResponseEntity.ok().build();
	}

	// To map with task
	@PutMapping("/employees/{empId}/task")
	public Employee updateEmployeeByTask(@Valid @RequestBody Set<Task> tasks,
			@PathVariable(value = "empId") Long empId) {

		Employee employee = employeeRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", empId));
		employee.setTasks(tasks);
		return employeeRepository.save(employee);

	}

	@GetMapping("/employees/{empId}/tasks")
	public Set<Task> getTaskByEmployee(@PathVariable(value = "empId") Long empId) {

		Employee employee = employeeRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", empId));
		return employee.getTasks(); 
	}

}

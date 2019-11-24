package com.example.demoapp.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.example.demoapp.model.Department;
import com.example.demoapp.repository.DepartmentRepository;

@RestController
public class DepartmentController {

	@Autowired
	DepartmentRepository departmentRepository;
	
    @GetMapping("/dpts")
    public List<Department> getAllDepartments() {
        //return (List<Department>) departmentRepository.findAll();

    	Iterable<Department> it = departmentRepository.findAll();
    	List departments = new ArrayList<Department>();
    	it.forEach(e -> departments.add(e));
    	return departments;	
    }

    @PostMapping("/dpts")
    public Department createDepartment(@Valid @RequestBody Department department) {
        return departmentRepository.save(department);
    }

    @GetMapping("/dpts/{id}")
    public Department getDepartmentById(@PathVariable(value = "id") Long dptId) {
        return departmentRepository.findById(dptId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", dptId));
    }

    @PutMapping("/dpts/{id}")
    public Department updateDepartment(@PathVariable(value = "id") Long dptId,
                                           @Valid @RequestBody Department dptDetails) {

        Department dpt = departmentRepository.findById(dptId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", dptId));

        dpt.setDptName(dptDetails.getDptName());

        Department updatedDepartment = departmentRepository.save(dpt);
        return updatedDepartment;
    }

    @DeleteMapping("/dpts/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable(value = "id") Long dptId) {
        Department dpt = departmentRepository.findById(dptId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", dptId));

        departmentRepository.delete(dpt);

        return ResponseEntity.ok().build();
    }
	
}

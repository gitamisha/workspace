package com.example.demoapp.model;

import java.io.Serializable;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Department implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dptId;
	
	@NotNull
	private String dptName;

	public Long getDptId() {
		return dptId;
	}

	public void setDptId(Long dptId) {
		this.dptId = dptId;
	}

	public String getDptName() {
		return dptName;
	}

	public void setDptName(String dptName) {
		this.dptName = dptName;
	}
}

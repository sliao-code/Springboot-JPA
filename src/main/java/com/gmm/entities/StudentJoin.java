package com.gmm.entities;

// This class is not persisted so no annotation is needed. It doesn't represent any database table.
// This entity represents the join between student and department

import java.io.Serializable;
public class StudentJoin implements Serializable { // need to implement Serializable to be converted to JSON.

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private Double cgpa;
	private String departmentName;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setCgpa(Double cgpa) {
		this.cgpa = cgpa;
	}
	
	public Double getCgpa() {
		return this.cgpa;
	}
	
	public String getDepartmentName() {
		return this.departmentName;
	}
	
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public StudentJoin(){
		super();
	}
	
	public StudentJoin(Integer id,String name,Double cgpa,String departmentName) {
		super();
		this.id = id;
		this.name = name;
		this.cgpa = cgpa;
		this.departmentName = departmentName;
	}
}
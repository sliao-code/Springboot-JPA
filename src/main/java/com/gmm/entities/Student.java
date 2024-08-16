package com.gmm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//Entity class Represents the record in student table

@Entity
@Table(name="students")
public class Student {

	public Student() {}
	
	public Student(int id, String name,double cgpa) {
		this.id = id;
		this.name = name;
		this.cgpa = cgpa;
	}
	
	public Student(int id,String name,double cgpa,Department department) {
		this.id = id;
		this.name = name;
		this.cgpa = cgpa;
		this.department = department;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",unique = true,nullable = false)
	private int id;
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="name",nullable = false)
	private String name;
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	@Column(name="cgpa",nullable = false)
	private double cgpa;
	public double getCgpa() {
		return this.cgpa;
	}
	
	public void setCgpa(double cgpa) {
		this.cgpa = cgpa;
	}
	
	@ManyToOne //Manytoone since many students belong to one department
	// In @JoinColumn, the name ("deptid") represents the column name of this table ("students"), which will be created
	// automatically by JPA. The database table "students" doesn't hold Department object. The annotation indicates it has
	// a column, "deptid" to represent Department. The referencedColumn ("id") represents the corresponding column in 
	// Table "departments", that should be joined with "deptid".
	@JoinColumn(name="deptid", referencedColumnName = "id")
	private Department department;
	public Department getDepartment() {
		return this.department;
	}
	
	public void setDepartment(Department department) {
		this.department= department;
	}
}
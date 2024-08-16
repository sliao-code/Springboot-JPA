package com.gmm.entities;

//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// Entity class Represents the record in department table

@Entity
@Table(name="departments")
public class Department {		
	
	//private Set<Student> students = new HashSet<Student>(0); // set since many students
	
	// These constructors are needed only if there are gonna be used in @Query("new Department() like....")
	public Department() {}
	
	public Department(String name) {
		this.name = name;
	}
	
	//public Department(String name,Set<Student> students) {
	//	this.name = name;
	//	this.students = students;
	//}	
	
	@Id //specified the primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) //uses the database identity column
	@Column(name="id",unique = true,nullable = false) //name is optional is variable name matches table field name
	private int id;
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="name",nullable = false) // nullable checks whether null accepted, before db throws error
	private String name;
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	// OneToMany association doesn't seem to be a good idea. The set of students in a department can be very large
	//Onetomany -- One department may contain many studentes
	/*
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "department") //FetchType.Lazy loads the entities only when necessary good when dealing with lots of records
	public Set<Student> getStudents(){
		return this.students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	} */
}
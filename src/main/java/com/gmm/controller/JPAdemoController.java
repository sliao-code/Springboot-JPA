package com.gmm.controller;


//Author: Muthu Mariyappan G

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gmm.entities.*;
import com.gmm.repositories.DepartmentRepository;
import com.gmm.repositories.StudentRepository;

@RestController //is a convenience annotation that adds @Controller and @ResponseBody annotations
public class JPAdemoController {
	
	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	StudentRepository studentRepository;

	private final Logger logger = LoggerFactory.getLogger(JPAdemoController.class);
	
	// This prints the joined view of Student and Department tables
	@GetMapping(value="/jointable")  
	public List<StudentJoin> displayJoinTable(){
		return this.studentRepository.join();     
	}
	
	// Finds and prints the topper - corresponding to cgpa
	@GetMapping(value="/student/topper")
	public StudentJoin getTopper(){
		return this.studentRepository.getTopper(); 
	}
	
	//Finds Student details for the given id 
	@GetMapping(value="/student/find/{id}")
	public Student find(@PathVariable Integer id){
		Student student = this.studentRepository.findById(id).get();
		logger.info("Student ID: " + student.getId());
		logger.info("Student Department: " + student.getDepartment().getName());
		return student;
	}
	
	@PostMapping(value="/student/insert")//inserts new student record into the table
	public Map<String,String> insertStudent(@RequestParam("name") String name,@RequestParam("cgpa") double cgpa,@RequestParam("deptid") int deptid){
		//RequestParam gets the value from url, eg: ?name=harry -- here harry mapped to String name
		Map<String,String> message = new LinkedHashMap<>();
		Student newStudent;
		if(this.departmentRepository.findById(deptid).isPresent()) {
			newStudent = new Student();
			newStudent.setName(name);
			newStudent.setCgpa(cgpa);
			newStudent.setDepartment(this.departmentRepository.findById(deptid).get());
			
			if(this.studentRepository.save(newStudent)!=null) {
				message.put("Success", "New student successfully added!");
				message.put("ID", String.valueOf(newStudent.getId()));
				message.put("Name", name);
				message.put("CGPA", cgpa+"");
				message.put("Department", this.departmentRepository.findById(deptid).get().getName());
			}
			else
				message.put("Error", "Error cannot add new student");
		}
		else {
			message.put("Error", "Department id "+deptid+" is not found");
		}
		return message;
	}

	@GetMapping("/department/find-students/{deptid}")
	public List <Student> getDepartmentStudentByDeptid(@PathVariable int deptid) {
		return departmentRepository.findStudentsByDepartmentId(deptid);
	}
	
}
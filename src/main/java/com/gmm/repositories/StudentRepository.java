package com.gmm.repositories;

//Author: Muthu Mariyappan G

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmm.entities.*;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>{
	
	// @Query and new methods are used when some operation cannot be done by simple CrudRepository methods alone
	
	// performs join between student and department
	// uses studentjoin to hold the projection from the join
	@Query("select new com.gmm.entities.StudentJoin(s.id,s.name,s.cgpa,d.name) from Student s join Department d on s.department.id =d.id") // and inner join
	public List<StudentJoin> join();
	
	//finds the topper in collge respect to the cgpa
	@Query("select new com.gmm.entities.StudentJoin(s.id,s.name,s.cgpa,d.name)from Student s join Department d on s.department.id = d.id where s.cgpa in (select max(cgpa) from Student)")
	public StudentJoin getTopper();	
	
}
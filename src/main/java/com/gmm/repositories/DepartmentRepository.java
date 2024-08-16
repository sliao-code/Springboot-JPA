package com.gmm.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmm.entities.Department;
import com.gmm.entities.Student;


@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer>{

    // Use JPQL to query a list of Student for a specific department id, since OneToMany is not defined in
    // Department entity.
    @Query("select u from Student u where u.department.id = ?1")
    public List <Student> findStudentsByDepartmentId(int id);
}
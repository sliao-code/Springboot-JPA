## Springboot JPA with CRUD and Join Operations

 This is a sample project in springboot and jpa(hibernate) using mysql database. 
 
 JPA stands for java persistence API which is a collection of classes and methods which is used to store large amount of data permanently to the database and it also reduces the amount of code needed and simplifieds database interactions. 
 
 JPA is a abstract idea(specification) and hibernate is a implemenation which comforms to the JPA specifications.
 
 This project has basic database operations in student table and join operation for student and department table
 
 For simplicity all requests are Get requests, please find below the sample get requests
 
 ### Student table: "students"
 ---------------
 
	 id primary key
	 name not null
	 cgpa not null
	 deptid foreign key references department table
	
 ### Department table: "departments"
 ------------------
	id
	name
	
For Table "students", its relation to Table "departments" is Many-to-One (many students can belongs to one department). One-to-Many for the opposite.

### Table Associations in JPA

As straightforward as it might be in a relational database, when it comes to JPA, the one-to-many database association can be represented either through a __@ManyToOne__ or a __@OneToMany__ association since the OOP association can be either unidirectional or bidirectional.

#### @ManyToOne Association
The __@ManyToOne__ annotation allows you to map the Foreign Key column in the child entity (e.g. Table "students" here) mapping so that the child has an entity object reference to its parent entity (e.g. Table "department" here). This is the most natural way of mapping a database one-to-many database association, and, usually, the __most efficient implementation__ too. The following shows the implementation.

```
@Entity
@Table(name="students")
public class Student {
	// other part of codes

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="deptid") // "depatid" is the column to store the primary ID of Table "departments".
	private Department department;
}

@Entity
@Table(name="departments")
public class Department {
	// without explicit association annotation
}
```
* In class "Student", the corresponding table in the database is called "students". It has a private member variable called "department" with type of ```Department```. In the database, the corresponding column is implemented as "depatid" as specified in ```@JoinColumn``` annotation. So the ```Student``` entity in Java codes stores the ```Department``` entity class; while the database table "students" stores only "deptid", the __Primary ID__ of Table "departments". JPS automatically makes the association.
* In class "Department", there is no annotation for association, so there is no column in Table "departments" for association or relation to Table "students". This is what we normally will do in designing database tables.
* Since class ```Department``` doesn't have variables to represent all the students belonging to the specific department, we will need to use SQL queries to get a list of students in the specific repository interface.
  ```
  @Query("select u from Student u where u.department.id = ?1")
  public List <Student> findStudentsByDepartmentId(int id);
  ```

#### @OneToMany Association (not recommended)
For convenience, to take advantage of the entity state transitions and the dirty checking mechanism, many developers choose to map the child entities as a collection in the parent object, and, for this purpose, JPA offers the __@OneToMany__ annotation. For example:
```
@Entity
@Table(name="departments")
public class Department {
	// other part of codes

	@OneToMany
	@JoinColumn(name="deptid")
	private Set <Student> students
	public Set <Student> getStudents(){
		return this.students;
	}
}
```
The good side of the above codes is that class ```Department``` has a list of ```Student``` as its member, so it is easy to get a list of students belongs to a specific department. But there is a also caveat as well. When there are a large number of students of a specific department, any reference to ```Department``` will result in querying a large number of student records even they are not needed.

To overcome the shortcoming mentioned above, using only  __@ManyToOne__ on the ```Student``` side and using __#Query__ in repository interface is preferred.

### JPA Query
There are many methods to query the database in JPA.

#### Derived Query Methods
For simple queries, it’s easy to derive what the query should be just by looking at the corresponding method name in our code. This is done automatically by JPA implementation.

Derived method names have two main parts separated by the first _By_ keyword:
```
List<User> findByName(String name)
```
The first part — such as _find_ — is the introducer, and the rest — such as _ByName_ — is the criteria.

Spring Data JPA supports _find_, _read_, _query_, _count_ and _get_. So, we could have done _queryByName_, and Spring Data would behave the same.

We can also use _Distinct_, _First_ or _Top_ to remove duplicates or limit our result set:
```
List<User> findTop3ByAge()
```
The criteria part contains the entity-specific condition expressions of the query. We can use the condition keywords along with the entity’s property names.

We can also concatenate the expressions with _And_ and _Or_, as we’ll see in the following example.
```
List<User> findByNameOrAge(String name, Integer age);
List<User> findByNameOrAgeAndActive(String name, Integer age, Boolean active);
```
See [more details](https://docs.spring.io/spring-data/jpa/docs/1.6.0.RELEASE/reference/html/jpa.repositories.html) about the declared query.

#### @Query with JPQL
For more complicated database queries, JPA offers an annotation ```@Query```. There are many ways to wriye ```@Query```. One of them is JPQL. The Java Persistence query language (JPQL) is used to define searches against persistent entities independent of the mechanism used to store those entities. As such, JPQL is "portable", and not constrained to any particular data store. JPQL is similar with SQL.
```
@Query("select u from Student u where u.department.id = ?1")
public List <Student> findStudentsByDepartmentId(int id);
```
In the above example, the syntax is similar with SQL, but the reference names are Java classes or entities, instead of database table or column names. Here class name "Student" is used instead of Table name "students". "u.department.id" is a Java reference name instead of table column reference.

See [JPQL Language Reference](https://docs.oracle.com/cd/E17904_01/apirefs.1111/e13946/ejb3_langref.html) for more details.

#### @Query with Native SQL
Native SQl can be used with ```@Query`` as well, but mapping the results from a native query to entity or a list of entities is more complicated.

	
### Implemented operations and sample url requests
------------------------------------------------
1. Find student by id

	> http://localhost:8080/student/find/1

2. Add new student entry to the table

	> http://localhost:8080/student/insert?name=kai&cgpa=9.87&deptid=1

3. Join student and department table

	> http://localhost:8080/jointable

4. Find the college topper with respect to cgpa

	> http://localhost:8080/student/topper

5. Find all students of a specific department by department id
   > http://localhost:8080/department/find-students/1
	
  ### Tips
  -------
  * use Annotations such as @Autowired over context.xml files
  * use application.properties to store datasource information
  * Follow same naming convention
  * Try to use same field name as table field name
  * Double check the component names
  
  ### Useful tutorial links
  ------------------------
  
* [Detailed and simple tutorial for springboot and jpa](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-introduction-to-query-methods/)

* Learn Spring Data JPA with Real Apps -- followed this app's project structure with changes to springboot
	
 
 
 

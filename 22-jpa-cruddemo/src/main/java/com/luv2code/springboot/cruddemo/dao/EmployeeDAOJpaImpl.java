package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

	private EntityManager entityManager;
	
	@Autowired
	public EmployeeDAOJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Employee> findAll() {
		//create query 
		Query theQuery =
				entityManager.createQuery("from Employee");
		
		//exeute query and get result list 
		List<Employee> employees = theQuery.getResultList();
		
		//return the results 
		
		return employees;
	}

	@Override
	public Employee findById(int theId) {
		//get employee 
		Employee employee =
				entityManager.find(Employee.class, theId);
		
		return employee;
	}

	@Override
	public void save(Employee theEmployee) {
		
		//save or update the employee (jpa does both)
		Employee dbEmployee = entityManager.merge(theEmployee);
		
		//update with id from db so it can get generated for save/insert 
		theEmployee.setId(dbEmployee.getId());
		
	}

	@Override
	public void deleteById(int theId) {
		//delete object with primary key 
		Query theQuery = entityManager.createQuery(
				"delete from Employee where id=:employeeId");

		//Set the id to the const id value
		theQuery.setParameter("employeeId", theId);
		
		theQuery.executeUpdate();
		
	}

}

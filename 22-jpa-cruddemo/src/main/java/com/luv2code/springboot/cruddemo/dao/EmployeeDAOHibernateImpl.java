package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

	
	//define field for entitymanager 
	private EntityManager entityManager;
	
	
	
	//set up constructor injection 
	@Autowired
	public EmployeeDAOHibernateImpl(EntityManager entityManager) {
		
		this.entityManager = entityManager;
		
	}
	
		//Would use @Transactional, will add a service to deal with @Transactional annotation
	@Override
	public List<Employee> findAll() {

		//get the current hibernate session by unwrapping the entity
		Session currentSession = entityManager.unwrap(Session.class);
		
		//Create the query (Import hibernate.query) QUERY DATABASE IS CASE SENSITIVE
		Query<Employee> theQuery = 
				currentSession.createQuery("from Employee", Employee.class);
		
		//execute query and get result list 
		List<Employee> employees = theQuery.getResultList();
		
		//return the results 
		
		return employees;
	}


	@Override
	public Employee findById(int theId) {
		
		//get the current hibernate session 
		Session currentSession = entityManager.unwrap(Session.class);
		
		
		//get the employee (single)
		Employee theEmployee = 
				currentSession.get(Employee.class, theId);
		
		//return the employee
		return theEmployee;
	}


	@Override
	public void save(Employee theEmployee) {
		//get the current hibernate session 
				Session currentSession = entityManager.unwrap(Session.class);
				
		//save employee
		currentSession.saveOrUpdate(theEmployee);
		
	}


	@Override
	public void deleteById(int theId) {
		
		//get the current hibernate session 
				Session currentSession = entityManager.unwrap(Session.class);
				
		//delete object with primary key (QUERY)
				Query theQuery =
						currentSession.createQuery(
								"delete from Employee where id=:empoyeeId");
				
				//set the paremeter to search by (constructor value)
				theQuery.setParameter("employeeId", theId);
				
				//commit action
				theQuery.executeUpdate();
				
	}

}

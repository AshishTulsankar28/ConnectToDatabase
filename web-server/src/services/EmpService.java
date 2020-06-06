package services;

import javax.persistence.Id;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.web.bind.annotation.RequestBody;

import views.Employees;

public interface EmpService {

	/**
	 * Method to fetch employee name using its unique primary key constraint
	 * @param empId as primary key 
	 * @return employee name
	 */
	public String getEmpName(int empId);
	/**
	 * Method to fetch employee details using its unique primary key constraint 
	 * @param empId as primary key 
	 * @return {@link Employees} associated with given employee ID
	 */
	public Employees getEmpDetails(int empId);
	/**
	 * Method to fetch maximum available employee ID
	 * @return highest employee number that exist in DB object
	 */
	public int getMaxEmpId();
	/**
	 * Method to INSERT new employee record
	 * @param emp {@link Employees} object received from {@link RequestBody}
	 * @return Associated employee Number as unique key constraint 
	 */
	public int addEmp(Employees emp);
	/**
	 * Method uses spring data repository to search employee using its {@link Id} attribute
	 * @param empId to be searched
	 * @return {@link Employees} object for the provided empId
	 */
	public Employees findEmpById(int empId);
	/**
	 * Method to insert {@link Employees} record using spring data repository
	 * @param emp represents object to be saved.
	 */
	public void persistEmp(Employees emp);
	/**
	 * Update {@link Employees} record using Spring declarative transaction
	 * @param emp {@link Employees} object with new/ updated values
	 */
	public void updateEmp(Employees emp);
	/**
	 * Delete {@link Employees} record using {@link HibernateTemplate}
	 * @param empId which is to be deleted
	 */
	public void deleteEmp(int empId);
}

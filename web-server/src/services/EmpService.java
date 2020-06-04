package services;

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
}

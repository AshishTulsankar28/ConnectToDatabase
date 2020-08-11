package services;

import java.util.List;

import views.Employees;

public interface TestService {

	/**
	 * Method to fetch employee name using its unique primary key constraint
	 * @param empId as primary key 
	 * @return employee name
	 */
	public String getAppName();
	/**
	 * Returns employee specific Id using given empId.
	 * @param empId if -1 then return list of available employees else return matching record.
	 * @return {@link List} of matching {@link Employees}
	 */
	public List<Employees> getEmpDetails(int empId);
	
}

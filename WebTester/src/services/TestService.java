package services;

public interface TestService {

	/**
	 * Method to fetch employee name using its unique primary key constraint
	 * @param empId as primary key 
	 * @return employee name
	 */
	public String getAppName();
	
}

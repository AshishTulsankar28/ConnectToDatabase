/**
 * 
 */
package services;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import views.Employees;


/**
 * @author Ashish Tulsankar
 *
 */
@Service
public class TestServiceImpl implements TestService {

	@Override
	public String getAppName() {
		return "JUnit5";
	}

	@Override
	public List<Employees> getEmpDetails(int empId) {
		// TODO Implement way to access details from DB & return actual List. As of now, creating a demo list manually.
		List<Employees> empList=new ArrayList<Employees>(4);

		Employees e1=new Employees(1, LocalDate.now(), "Ashish", "Tulsankar", 'M', LocalDate.now());
		empList.add(e1);

		if(empId>0) {
			Employees e2=new Employees(2, LocalDate.now(), "Abhishek", "Deshpande", 'M', LocalDate.now());
			empList.add(e2);

			Employees e3=new Employees(3, LocalDate.now(), "Akshay", "Turate", 'M', LocalDate.now());
			empList.add(e3);

			Employees e4=new Employees(4, LocalDate.now(), "Sunita", "Pawar", 'F', LocalDate.now());
			empList.add(e4);
		}

		return empList;
	}



}

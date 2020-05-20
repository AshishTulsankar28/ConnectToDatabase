/**
 * 
 */
package services;

import org.springframework.stereotype.Service;

/**
 * @author Ashish Tulsankar
 *
 */
@Service
public class EmpServiceImpl implements EmpService {

	@Override
	public String getEmpName(int empId) {
		return "Ashish Tulsankar";
	}

}

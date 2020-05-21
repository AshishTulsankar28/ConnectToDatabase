/**
 * 
 */
package services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author Ashish Tulsankar
 *
 */
@Service
public class EmpServiceImpl implements EmpService {
	
	Logger logger=LogManager.getLogger();
	
	public EmpServiceImpl() {
		logger.trace("WEBSERVER - EmpServiceImpl Default Constructor invoked ");
	}

	@Override
	public String getEmpName(int empId) {
		return "Ashish Tulsankar";
	}

}

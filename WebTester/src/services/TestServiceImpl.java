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
public class TestServiceImpl implements TestService {

	@Override
	public String getAppName() {
		return "JUnit5";
	}


}

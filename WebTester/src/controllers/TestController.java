/**
 * 
 */
package controllers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import services.TestService;

/**
 * @author Ashish Tulsankar
 *
 */
@RestController
public class TestController {

	Logger logger=LogManager.getLogger();

	@Autowired
	TestService testService;

	/**
	 *  Root URL that will be invoked on application start up
	 *  but it will prevent view from rendering 
	 *  & will execute the function written in below method
	 */
	@RequestMapping(value="/",method= RequestMethod.GET)
	public String home() {
		return testService.getAppName()+" application started successfully !";
	}

}

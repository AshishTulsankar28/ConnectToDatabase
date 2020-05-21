/**
 * 
 */
package controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import services.EmpService;
import views.ResponseVM;

/**
 * @author Ashish Tulsankar
 *
 */
@RestController
public class EmpController extends WebController{

	Logger logger=LogManager.getLogger();
	
	public EmpController() {
		logger.trace("WEBSERVER - EmpController constructor invoked");
	}

	@Autowired
	EmpService empService;

	//		/**
	//		 *  Root URL that will be invoked on application start up
	//		 *  but it will prevent view from rendering 
	//		 *  & will execute the function written in below method
	//		 */
	//	@RequestMapping(value="/",method= RequestMethod.GET)
	//	public String home() {
	//		logger.info("WEBSERVER - home() called"); 
	//		return "home";
	//	}

	@RequestMapping(value="/getEmpName",method= RequestMethod.GET)
	public String getEmpName() {
		logger.trace("WEBSERVER - getEmpName() called"+empService.getEmpName(0)); 
		return empService.getEmpName(0);

	}

	@RequestMapping(value="/demo",method= RequestMethod.GET)
	public ResponseVM demo() {
		logger.trace("WEBSERVER - getEmpName() called"+empService.getEmpName(0));

		ResponseVM response=new ResponseVM();
		response.setResponseData(empService.getEmpName(0));
		response.setStatus(HttpStatus.OK);

		return response;
	}

}

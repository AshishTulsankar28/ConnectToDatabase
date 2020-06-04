/**
 * 
 */
package controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import services.EmpService;
import views.Employees;
import views.ResponseVM;

/**
 * @author Ashish Tulsankar
 *
 */
@RestController
public class EmpController extends WebController{

	Logger logger=LogManager.getLogger();
	@Autowired
	EmpService empService;


	public EmpController() {
		// DO nothing
		logger.trace("WEBSERVER - EmpController constructor invoked");
	}

	@RequestMapping(value="/getEmpName",method= RequestMethod.GET)
	public ResponseVM getEmpName(@RequestParam int empId) {
		logger.trace("WEBSERVER - getEmpName() called"+empId); 

		ResponseVM response=new ResponseVM();
		response.setResponseData(empService.getEmpName(empId));
		response.setStatus(HttpStatus.OK);

		return response;
	}

	@RequestMapping(value="/getEmpDetails",method= RequestMethod.GET)
	public ResponseVM getEmpDetails(@RequestParam int empId) {
		logger.trace("WEBSERVER - getEmpDetails() called"+empId);

		ResponseVM response=new ResponseVM();
		response.setResponseData(empService.getEmpDetails(empId));
		response.setStatus(HttpStatus.OK);

		return response;
	}

	@RequestMapping(value="/addEmp",method=RequestMethod.POST)
	public ResponseVM addEmp(@RequestBody Employees emp) {

		ResponseVM response=new ResponseVM();

		if(emp!=null) {
			emp.setEmpNo(empService.getMaxEmpId()+1);
			response.setResponseData(empService.addEmp(emp));
		}

		return response;

	}


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

}

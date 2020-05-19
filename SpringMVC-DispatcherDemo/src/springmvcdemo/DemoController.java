/**
 * 
 */
package springmvcdemo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * 
 * @author Ashish Tulsankar
 *
 */
@RestController
public class DemoController extends AbstractController {

	@Autowired
	DemoService demoService;

	@RequestMapping(value = "/getAuthName", method = RequestMethod.GET)
	public void getAuthName() {
		System.out.println("getAuthName method called "+demoService.getAuthorName());
	}

	@RequestMapping(value = "/getName/{name}", method = RequestMethod.GET)
	public void getName(@PathVariable String name) {
		System.out.println("getName method called "+name);
	}
	
	@RequestMapping(value = "/getRequestParam", method = RequestMethod.GET)
	public void getRequestParam(@RequestParam String name) {
		System.out.println("getRequestParam method called "+name);
	}
	
	@RequestMapping(value = "/getDetails", method = RequestMethod.POST)
	public void getDetails(@RequestBody ViewModel vm ) {
		System.out.println("getDetails method called "+vm.getName());
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv=new ModelAndView("viewName", HttpStatus.ACCEPTED);
		return mv;
	}



}

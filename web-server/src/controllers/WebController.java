package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class WebController extends AbstractController{

	Logger logger=LogManager.getLogger();
	
	public WebController() {
		//logger.trace("WEBSERVER - WebController default constructor invoked");
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.trace("WEBSERVER - handleRequestInternal - "+request+" response - "+response);
		return new ModelAndView();
	}

}

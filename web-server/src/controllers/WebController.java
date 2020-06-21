package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class WebController extends AbstractController{

	Logger logger=LogManager.getLogger();
	
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("WEBSERVER - handleRequestInternal - "+request+"response "+response+" response Code- "+response.getStatus());
		return new ModelAndView();
	}

}

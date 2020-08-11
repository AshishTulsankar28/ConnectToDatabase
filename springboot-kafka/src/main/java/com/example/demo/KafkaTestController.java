package com.example.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@RestController
public class KafkaTestController extends AbstractController {

	private final Producer kafkaProducer;

	@Autowired
	public KafkaTestController(Producer prod) {
		this.kafkaProducer=prod;
	}

	@RequestMapping(value="/publish",method = RequestMethod.POST)
	public String msgToTopic(@RequestParam String msg) {
		this.kafkaProducer.sendMessage("Hello Ashish");

		//		for(int i=0;i<5;i++) {
		//			this.kafkaProducer.sendMessage(String.valueOf(i));
		//		}

		return "Success !";
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO return new ModelAndView();
		return null;
	}


}

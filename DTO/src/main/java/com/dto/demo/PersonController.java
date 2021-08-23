package com.dto.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PersonController {

	@Autowired
	private PersonService personService;

	@GetMapping("/getPerson")
	ResponseDto<PersonDto> getPerson() {
		return ResponseDto.accepted().convertTo(personService.findOneById(1L), PersonDto.class);
	}
	
	@PostMapping("/sendPersonDto")
	ResponseDto<PersonDto> sendAndGet(@RequestDto(PersonDto.class) @Validated Person person) {
		System.out.println("Received as person >> " + person.getFirstName());
		return ResponseDto.accepted().convertTo(personService.findOneById(1L), PersonDto.class);
	}
}

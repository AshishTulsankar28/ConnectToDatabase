package com.dto.demo;

import org.springframework.stereotype.Service;

@Service
public class PersonService {

	public Person findOneById(Long id) {
        Person person = new Person();
        person.setId(id);
        person.setFirstName("Ashish");
        person.setLastName("Tulsankar");
        person.setGender("Male");
        person.setFullName("Ashish Parashram Tulsankar");
        person.setAddress("Satara");
		return person ;
    }
	
}

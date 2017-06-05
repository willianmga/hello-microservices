package com.matera.hellomicroservices.converter;

import com.matera.hellomicroservices.entities.Person;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PersonResource;

public class PersonConverter {
	
	private PersonConverter() {
		
	}
	
	public static Person convertToPerson(CreatePersonRequest request) {
		
		Person person = new Person();
		person.setFirstName(request.getFirstName());
		person.setLastName(request.getLastName());
		person.setEmail(request.getEmail());
		person.setNickName(request.getNickName());
		return person;
		
	}

	public static CreatePersonResponse convertToResponse(Person person) {
		
		CreatePersonResponse response = new CreatePersonResponse();
		response.setId(person.getId());
		return response;
		
	}

	public static PersonResource converToPersonResource(Person person) {
		
		PersonResource resource = new PersonResource();
		resource.setUuid(person.getId());
		resource.setFirstName(person.getFirstName());
		resource.setLastName(person.getLastName());
		resource.setNickName(person.getNickName());
		resource.setEmail(person.getEmail());
		return resource;
		
	}

}


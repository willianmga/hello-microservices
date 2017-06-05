package com.matera.hellomicroservices.converter;

import java.util.UUID;

import com.matera.hellomicroservices.entities.Person;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.requests.FindPersonByUUIDRequest;
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
	
	public static Person convertToPerson(FindPersonByUUIDRequest request) {
		
		Person person = new Person();
		person.setId(UUID.fromString(request.getId()));
		return person;
		
	}	

	public static CreatePersonResponse convertToResponse(Person person) {
		CreatePersonResponse response = new CreatePersonResponse();
		response.setId(person.getId().node());
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


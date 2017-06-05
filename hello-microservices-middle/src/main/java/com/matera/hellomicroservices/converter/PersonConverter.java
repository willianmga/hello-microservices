package com.matera.hellomicroservices.converter;

import com.matera.hellomicroservices.entities.Address;
import com.matera.hellomicroservices.entities.Person;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.AddressResource;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PersonResource;

public class PersonConverter {
	
	private PersonConverter() {
		
	}
	
	public static Person convertToPerson(CreatePersonRequest request) {
		
		Address address = new Address();
		address.setCity(request.getCity());
		address.setCountry(request.getCountry());
		address.setState(request.getState());
		address.setZipCode(request.getZipCode());
		
		Person person = new Person();
		person.setFirstName(request.getFirstName());
		person.setLastName(request.getLastName());
		person.setEmail(request.getEmail());
		person.setNickName(request.getNickName());
		person.setAddress(address);
		
		return person;
		
	}

	public static CreatePersonResponse convertToResponse(Person person) {
		
		CreatePersonResponse response = new CreatePersonResponse();
		response.setId(person.getId());
		return response;
		
	}

	public static PersonResource converToPersonResource(Person person) {
		
		AddressResource address = PersonConverter.convertToAddressResource(person.getAddress());
		
		PersonResource resource = new PersonResource();
		resource.setUuid(person.getId());
		resource.setFirstName(person.getFirstName());
		resource.setLastName(person.getLastName());
		resource.setNickName(person.getNickName());
		resource.setEmail(person.getEmail());
		resource.setAddress(address);
		return resource;
		
	}
	
	public static AddressResource convertToAddressResource(Address address) {
		
		AddressResource resource = new AddressResource();
		resource.setCity(address.getCity());
		resource.setCountry(address.getCountry());
		resource.setState(address.getState());
		resource.setZipCode(address.getZipCode());
		return resource;
		
	}

}


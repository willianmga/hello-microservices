package com.matera.hellomicroservices.converter;

import java.util.UUID;

import com.matera.hellomicroservices.entities.Address;
import com.matera.hellomicroservices.entities.Person;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.AddressResource;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PersonResource;

public class PersonConverter {
	
	private PersonConverter() {
		
	}
	
	public static Person convertToPerson(CreatePersonRequest request, UUID personID) {
		
		Address address = new Address.Builder()
					.withCity(request.getCity())
					.withCountry(request.getCountry())
					.withState(request.getState())
					.withZipCode(request.getZipCode())
					.build();
		
		Person person = new Person.Builder()
				.withUUID(personID)
				.withFirstName(request.getFirstName())
				.withLastName(request.getLastName())
				.withEmail(request.getEmail())
				.withNickName(request.getNickName())
				.withAddress(address)
				.build();
		
		return person;
		
	}

	public static CreatePersonResponse convertToResponse(Person person, String message) {
		
		CreatePersonResponse response = new CreatePersonResponse.Builder()
				.withID(person.getId())
				.withMessage(message)
				.build();

		return response;
		
	}

	public static PersonResource converToPersonResource(Person person) {
		
		AddressResource address = AddressConverter.convertToAddressResource(person.getAddress());
		
		PersonResource resource = new PersonResource.Builder()
				.withUUID(person.getId())
				.withFirstName(person.getFirstName())
				.withLastName(person.getLastName())
				.withEmail(person.getEmail())
				.withNickName(person.getNickName())
				.withAddress(address)
				.build();

		return resource;
		
	}

}



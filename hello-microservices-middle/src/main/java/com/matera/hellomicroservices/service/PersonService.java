package com.matera.hellomicroservices.service;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.UUID;

import com.google.inject.Inject;
import com.matera.hellomicroservices.converter.PersonConverter;
import com.matera.hellomicroservices.entities.Person;
import com.matera.hellomicroservices.store.PersonStore;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PeopleResource;
import matera.com.hellomicroservices.core.responses.PersonResource;

public class PersonService {
	
	private final PersonStore store;

	@Inject
	public PersonService(PersonStore store) {
		this.store = store;
	}
	
	public CreatePersonResponse insert(CreatePersonRequest request) {

		Person person = PersonConverter.convertToPerson(request, UUID.randomUUID());

		try {
			
			store.insert(person);

			CreatePersonResponse response = PersonConverter.convertToResponse(person, "Success");
		
			return response;
			
		} catch(Exception e) {
			
			throw new RuntimeException("Failed to insert person. \n\n"
									 + "Reason: " + e.getMessage() +
					  					"Try again.");
		}
		
	}
	
	public PeopleResource findAllPeople() {
		
		List<Person> people = store.findAllPeople();
		
		PeopleResource peopleResource = PersonConverter.convertToPeopleResource(people);

		return peopleResource;
		
	}	

	public PersonResource findByUUID(UUID uuid) {

		checkArgument(uuid != null, "the id must not be null");
				 
		Person found = store.getById(uuid);
		
		if (found == null)
			throw new IllegalArgumentException();
		
		PersonResource resource = PersonConverter.converToPersonResource(found);
		
		return resource;
		
	}

	public PeopleResource findPeopleByZipCode(String zipCode) {
		
		checkArgument(zipCode != null, "the zip code must not be null");
		
		List<Person> people = store.findByZipCode(zipCode);
		
		PeopleResource peopleResource = PersonConverter.convertToPeopleResource(people);
		
		return peopleResource;
		
	}

	public PeopleResource findByFirstAndOrLastName(String firstName, String lastName) {
		
		List<Person> people = store.findByFirstAndOrLastName(firstName, lastName);
		
		PeopleResource peopleResource = PersonConverter.convertToPeopleResource(people);
		
		return peopleResource;
		
	}
}

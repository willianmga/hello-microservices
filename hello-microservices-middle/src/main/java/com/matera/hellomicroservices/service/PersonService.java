package com.matera.hellomicroservices.service;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.matera.hellomicroservices.converter.PersonConverter;
import com.matera.hellomicroservices.entities.Person;
import com.matera.hellomicroservices.queries.PersonFilter;
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

		try {
			
			Person person = PersonConverter.convertToPerson(request, UUID.randomUUID());
			
			store.insert(person);

			return PersonConverter.convertToResponse(person, "Success");
			
		} catch(Exception e) {
			
			throw new RuntimeException("Failed to insert person. \n\n"
									 + "Reason: " + e.getMessage() +
					  					"Try again.");
		}
		
	}
	
	public CreatePersonResponse update(String id, CreatePersonRequest request) {
	
		try {
		
			Person person = PersonConverter.convertToPerson(request, UUID.fromString(id));
			
			store.update(person);
			
			return PersonConverter.convertToResponse(person, "Success");
			
		} catch(Exception e) {
			
			throw new RuntimeException("Failed to update person. \n\n" + 
									   "Reason: " + e.getMessage());
			
		}
	}
	
	
	public PeopleResource findAllPeople(PersonFilter filter) {
		
		List<Person> people = 
			store.findAllPeople()
				.stream()
				.filter(filter)
				.collect(Collectors.toList());
		
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

}

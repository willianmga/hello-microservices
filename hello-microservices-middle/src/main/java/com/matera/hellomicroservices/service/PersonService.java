package com.matera.hellomicroservices.service;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.matera.hellomicroservices.converter.PersonConverter;
import com.matera.hellomicroservices.entities.Person;
import com.matera.hellomicroservices.store.PersonStore;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PersonResource;

public class PersonService {
	
	private final PersonStore store;

	@Inject
	public PersonService(PersonStore store) {
		this.store = store;
	}
	
	public CreatePersonResponse insert(CreatePersonRequest request) {
		
		Person person = PersonConverter.convertToPerson(request);
		
		checkArgument(StringUtils.isNotBlank(person.getFirstName()), "the first name must not be null");
		checkArgument(StringUtils.isNotBlank(person.getLastName()), "the last name must not be null");
		checkArgument(StringUtils.isNotBlank(person.getEmail()), "the email must not be null");
		checkArgument(StringUtils.isNotBlank(person.getNickName()), "the nickname must not be null");
		
		Person inserted = store.insert(person);
		
		CreatePersonResponse response = PersonConverter.convertToResponse(inserted);
		response.setMessage("Success");		
		
		return response;
		
	}

	public PersonResource findByUUID(UUID uuid) {

		checkArgument(uuid != null, "the id must not be null");
				 
		Person found = store.getById(uuid);
		
		PersonResource resource = PersonConverter.converToPersonResource(found);
		
		return resource;
		
	}
}

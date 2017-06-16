package com.matera.microservices.service;

import java.util.UUID;

import com.google.inject.Inject;
import com.matera.hellomicroservices.queries.PersonQuery;
import com.matera.microservices.api.PersonClient;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PeopleResource;
import matera.com.hellomicroservices.core.responses.PersonResource;
import rx.Observable;

public class PersonService {
	
	private final PersonClient client;
	
	@Inject
	public PersonService(PersonClient client) {
		this.client = client;
	}

	public Observable<CreatePersonResponse> createPerson(CreatePersonRequest person) {
		
		return client.createPerson(person);
		
	}

	public Observable<PeopleResource> findAllPeople(PersonQuery query) {
		
		return client.searchBy(query);
		
	}

	public Observable<PersonResource> findPersonByUUID(String uuid) {
		
		try {
			return client.searchPersonByUUID(UUID.fromString(uuid));
		} catch (Exception e) {
			return null;	
		}
		
	}

}

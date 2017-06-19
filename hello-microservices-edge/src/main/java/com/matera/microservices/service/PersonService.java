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

	public Observable<CreatePersonResponse> create(CreatePersonRequest person) {
		
		return client.create(person);
		
	}
	
	public Observable<CreatePersonResponse> update(String uuid, CreatePersonRequest person) {
		
		try {
			return client.update(UUID.fromString(uuid), person);
		} catch(IllegalArgumentException e) {
			return null;
		}
		
	}		

	public Observable<PersonResource> findByUUID(String uuid) {
		
		try {
			return client.searchByUUID(UUID.fromString(uuid));
		} catch(IllegalArgumentException e) {
			return null;
		}
				
	}
	
	public Observable<PeopleResource> findAll(PersonQuery query) {
		
		return client.searchBy(query);
		
	}

}

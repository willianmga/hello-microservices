package com.matera.microservices.service;

import com.google.inject.Inject;
import com.matera.microservices.api.PersonClient;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
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

}

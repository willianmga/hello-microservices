package com.matera.microservices.service;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import rx.Observable;

public class PersonService {
	
//	private final PersonClient client;
//	
//	@Inject
//	public PersonService(PersonClient client) {
//		this.client = client;
//	}

	public Observable<CreatePersonResponse> createPerson(CreatePersonRequest person) {
		
		//return client.createPerson(person);
		return null;
		
	}

}

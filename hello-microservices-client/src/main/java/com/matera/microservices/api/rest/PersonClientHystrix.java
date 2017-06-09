package com.matera.microservices.api.rest;

import com.matera.microservices.api.PersonClient;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import rx.Observable;

public class PersonClientHystrix implements PersonClient {

	
	public PersonClientHystrix() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Observable<CreatePersonResponse> createPerson(CreatePersonRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}

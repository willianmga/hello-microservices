package com.matera.microservices.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.matera.microservices.api.PersonClient;
import com.matera.microservices.api.command.PersonCreateCommand;
import com.netflix.niws.client.http.RestClient;

import matera.com.hellomicroservices.core.config.HelloMicroservices;
import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import rx.Observable;

@SuppressWarnings("deprecation")
public class HystrixPersonClient implements PersonClient {
	
	private final RestClient client;
	private final ObjectMapper mapper;
	
	@Inject
	public HystrixPersonClient(@Named("ValidationRestClient") RestClient client, @HelloMicroservices ObjectMapper mapper) {
		this.client = client;
		this.mapper = mapper;
	}

	@Override
	public Observable<CreatePersonResponse> createPerson(CreatePersonRequest request) {
		return new PersonCreateCommand(client, mapper, request).observe();
	}

}

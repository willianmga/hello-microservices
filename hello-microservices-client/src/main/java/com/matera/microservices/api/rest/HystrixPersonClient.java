package com.matera.microservices.api.rest;

import org.apache.http.client.HttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.matera.microservices.api.PersonClient;
import com.matera.microservices.api.command.PersonCreateCommand;

import matera.com.hellomicroservices.core.config.HelloMicroservices;
import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import rx.Observable;

public class HystrixPersonClient implements PersonClient {
	
	private final ObjectMapper mapper;
	private HttpClient client;
	
	@Inject
	public HystrixPersonClient(@HelloMicroservices HttpClient provider,  @HelloMicroservices ObjectMapper mapper) {
		this.client = provider;
		this.mapper = mapper;
	}

	@Override
	public Observable<CreatePersonResponse> createPerson(CreatePersonRequest request) {
		
		return new PersonCreateCommand(client, mapper, request).observe();
		
	}

}

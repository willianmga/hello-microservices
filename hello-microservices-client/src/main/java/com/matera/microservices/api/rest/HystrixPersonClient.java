package com.matera.microservices.api.rest;

import java.util.UUID;

import org.apache.http.client.HttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.matera.hellomicroservices.queries.PersonQuery;
import com.matera.microservices.api.PersonClient;
import com.matera.microservices.api.command.PersonCreateCommand;
import com.matera.microservices.api.command.PersonFindAllPeopleCommand;
import com.matera.microservices.api.command.PersonFindByIdCommand;

import matera.com.hellomicroservices.core.config.HelloMicroservices;
import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PeopleResource;
import matera.com.hellomicroservices.core.responses.PersonResource;
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

	@Override
	public Observable<PersonResource> searchPersonByUUID(UUID id) {
		
		return new PersonFindByIdCommand(client, mapper, id).observe();

	}

	@Override
	public Observable<PeopleResource> searchBy(PersonQuery personQuery) {
		
		return new PersonFindAllPeopleCommand(client, mapper, personQuery).observe();
		
	}

}

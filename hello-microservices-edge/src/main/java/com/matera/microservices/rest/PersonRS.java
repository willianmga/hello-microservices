package com.matera.microservices.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.matera.microservices.service.PersonService;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;

@Path("/persons")
public class PersonRS {
	
	private final PersonService service;
	
	@Inject
	public PersonRS(PersonService service) {
		this.service = service;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPerson(CreatePersonRequest person) {
		
		CreatePersonResponse response = service.createPerson(person).toBlocking().single();
		return Response.ok(response).build();
		
	}
	

}

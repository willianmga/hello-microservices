package com.matera.hellomicroservices.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.matera.hellomicroservices.service.PersonService;

import matera.com.hellomicroservices.core.entities.Person;

@Path("/persons")
public class PersonRS {
	
	private PersonService service;

	@Inject
	public PersonRS(PersonService service) {
		this.service = service;
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPerson(Person person) {
		
		Person personResponse = service.insert(person);
		return Response.ok(personResponse).build();
		
	}
	

}

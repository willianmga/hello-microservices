package com.matera.microservices.rest;

import java.util.NoSuchElementException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.matera.hellomicroservices.queries.PersonQuery;
import com.matera.microservices.service.PersonService;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PeopleResource;
import matera.com.hellomicroservices.core.responses.PersonResource;

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
		
		try {
		
			CreatePersonResponse response = service.createPerson(person).toBlocking().single();
			
			return Response.status(Status.CREATED)
						   .header("location", "/persons/" + response.getId())
						   .entity(response)
						   .build();
		
		} catch (NoSuchElementException | NullPointerException e) {
			
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
			
		}
		
	}
	
	@GET
	@Path("/{personUUID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findPersonByUUID(@PathParam("personUUID") String uuid) {
		
		try {
		
			PersonResource person = service.findPersonByUUID(uuid).toBlocking().single();
			return Response.ok(person).build();
								
		} catch (NoSuchElementException | NullPointerException e) {
			
			return Response.status(Status.NOT_FOUND).build();
			
		}
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllPeople(
						@QueryParam("firstName") String firstName, 
						@QueryParam("lastName") String lastName, 
						@QueryParam("zipCode") String zipCode) {
		
		PersonQuery query = new PersonQuery.Builder()
				.withFirstName(firstName)
				.withLastName(lastName)
				.withZipCode(zipCode)
				.build();
		
		try {
		
			PeopleResource people = service.findAllPeople(query).toBlocking().single();
			return Response.ok(people).build();
		
		} catch(NoSuchElementException | NullPointerException e) {
			
			return Response.status(Status.NOT_FOUND).build();
			
		}
		
	}
	
}

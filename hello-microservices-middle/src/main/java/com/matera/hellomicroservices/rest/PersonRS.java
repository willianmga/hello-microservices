package com.matera.hellomicroservices.rest;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.matera.hellomicroservices.queries.PersonFilter;
import com.matera.hellomicroservices.service.PersonService;
import com.sun.jersey.api.client.ClientResponse.Status;

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
	public Response create(CreatePersonRequest request) {

		CreatePersonResponse response = service.insert(request);
		
		String location = "/persons/" + response.getId();
		
		return Response.ok(response).header("location", location).build();
		
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") String id, CreatePersonRequest request) {
		
		CreatePersonResponse response = service.update(id, request);
		
		String location = "/persons/" + response.getId();
		
		return Response.ok(response).header("location", location).build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(
			@QueryParam("firstName") String firstName, 
			@QueryParam("lastName") String lastName,
			@QueryParam("zipCode") String zipCode) {

		PersonFilter filter = new PersonFilter.Builder()
				.withFirstName(firstName)
				.withLastName(lastName)
				.withZipCode(zipCode)
				.build();
		
		final PeopleResource people = service.findAllPeople(filter);
		
		Response response = (people.getPeopleResource().isEmpty()) ? 
								Response.status(Status.NOT_FOUND).build() : 
									Response.ok(people).build();  
		
		return response;
		
	}
	
	@GET
	@Path("/{userUUID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByUUID(@PathParam("userUUID") String userUUID) {
		
		PersonResource person; 
		
		try {
			person = service.findByUUID(UUID.fromString(userUUID));
		} catch(IllegalArgumentException e) {
			person = null;
		}
		
		Response response = (person == null) ?
								Response.status(Status.NOT_FOUND).build() :
									Response.ok(person).build();
		
		return response;
				
	}	

}

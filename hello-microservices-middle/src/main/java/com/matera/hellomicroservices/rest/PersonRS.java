package com.matera.hellomicroservices.rest;

import java.util.ArrayList;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.matera.hellomicroservices.service.PersonService;
import com.sun.jersey.api.client.ClientResponse.Status;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
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
	public Response addPerson(CreatePersonRequest request) {
		
		try {

			CreatePersonResponse response = service.insert(request);
			
			String location = "/persons/" + response.getId();
			
			return Response.ok(response).header("location", location).build();
		
		} catch (RuntimeException e) {
			
			return Response.status(Status.INTERNAL_SERVER_ERROR)
						.header("error-message", e.getMessage())
					    .build();
			
		}
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllPersons() {
		
		// to be implemented
		
		return Response.ok(new ArrayList<PersonResource>()).build();
	}
	
	@GET
	@Path("/{userUUID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findPersonByUUID(@PathParam("userUUID") String userUUID) {

		// to be adjusted
		
		PersonResource resource = service.findByUUID(UUID.fromString(userUUID));
		return Response.ok(resource).build();
	}	

}

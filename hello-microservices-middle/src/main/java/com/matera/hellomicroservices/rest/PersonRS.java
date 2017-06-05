package com.matera.hellomicroservices.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.matera.hellomicroservices.service.PersonService;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.requests.FindPersonByUUIDRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PersonResource;

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
	public Response addPerson(CreatePersonRequest request) {
		
		System.out.println("Criando pessoa " + request.getFirstName());
		CreatePersonResponse response = service.insert(request);
		System.out.println("Pessoa criada com sucesso");
		
		String location = "/persons/" + String.valueOf(response.getId());
		
		return Response.ok(response).header("location", location).build();
		
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findPersonByUUID(FindPersonByUUIDRequest request) {

		PersonResource resource = service.findByUUID(request);
		return Response.ok(resource).build();
		
	}	
	

}

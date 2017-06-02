package com.matera.hellomicroservices.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class HelloRS {
	
	public HelloRS() {
		
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response helloWorld() {
		
		return Response.ok("Hello World").build();
		
	}

	/*@GET
	@Path("person")
	@Produces({ 
		MediaType.APPLICATION_XML, 
		MediaType.APPLICATION_JSON 
	})
	public Response sayHelloToPerson() {
		
		Person person = new Person();
		person.setFirstName("William");
		
		return Response.ok(person).build();
	} */
	
}

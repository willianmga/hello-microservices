package com.matera.microservices.rest;

import static com.jayway.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import com.jayway.restassured.http.ContentType;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;

public class PersonRSIT {

	public void createPersonOk() {
		
		new MockServerClient("hellomicroservicesmiddle", 8080).when(
				HttpRequest.request("/hellomicroservicesmiddle/persons").withMethod("POST"))
				.respond(
						HttpResponse.response()
							.withBody("{"
									+ "\"personID\": \"00000001\","
									+ "\"message\": \"Success\""
									+ "}")
							.withHeader("Content-Type", "application/json")
							.withHeader("location", "/persons/00000001")
							.withStatusCode(200));
		
		CreatePersonRequest person = new CreatePersonRequest.Builder()
				.withFirstName("Willian")
				.withLastName("Azevedo")
				.withEmail("willian-mga@hotmail.com")
				.withNickName("bili")
				.withCity("Maringa")
				.withState("Parana")
				.withCountry("Brazil")
				.withZipCode("87025640")
				.build();
		
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(person)
		.when()
			.post("/persons")
		.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.header("location", Matchers.notNullValue());
		
	}
}

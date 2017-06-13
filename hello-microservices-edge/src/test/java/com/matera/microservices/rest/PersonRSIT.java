package com.matera.microservices.rest;

import static com.jayway.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;

public class PersonRSIT {

	@Test
	public void createPersonOk() {
		
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
			.post("http://hellomicroservicesmiddle:8080/hellomicroservicesmiddle/persons")
		.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.header("location", Matchers.notNullValue());
		
	}

}

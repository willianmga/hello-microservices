package com.matera.hellomicroservices.rest;

import static com.jayway.restassured.RestAssured.given;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;

public class PersonRSIT {

	@BeforeClass
	public static void setup() {
		
		RestAssured.port = 9080;
		RestAssured.basePath = "/hellomicroservicesmiddle/";
		RestAssured.baseURI = "http://localhost";
		
	}

	@Test
	public void createPeopleOk() {
		
		CreatePersonRequest person = newCreatePersonRequest();
		
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

	@Test
	public void createAndUpdatePersonOk() {
		
		// Creates the person

		String personID = createPerson(newCreatePersonRequest());
			
		// updates the person
		
		CreatePersonRequest updated = new CreatePersonRequest.Builder()
				.withFirstName("Paulo")
				.withLastName("Almeida")
				.withEmail("palmeida@hotmail.com")
				.withNickName("paulo")
				.withCity("Maringá")
				.withState("Paraná")
				.withCountry("Brasil")
				.withZipCode("87053000")
				.build();
		
		given()
			.pathParam("id", personID)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(updated)
		.when()
			.put("/persons/{id}")
		.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.header("location", Matchers.notNullValue());
		
	}

	@Test
	public void createAndDeletePersonOk() {
		
		// Creates the person

		String personID = createPerson(newCreatePersonRequest());
		
		// delete the person
		
		given()
			.pathParam("id", personID)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.delete("/persons/{id}")
		.then()
			.statusCode(200)
			.header("message", Matchers.notNullValue());		
		
	}
	
	@Test
	public void deletePersonThroughInvalidID() {

		final String invalidID = "12345";
		
		given()
			.pathParam("id", invalidID)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.delete("/persons/{id}")
		.then()
			.statusCode(400)
			.header("message", Matchers.notNullValue());
		
	}
	
	@Test
	public void deletePersonThroughInexistentID() {
		
		final String inexistentID = UUID.randomUUID().toString();
		
		given()
			.pathParam("id", inexistentID)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.delete("/persons/{id}")
		.then()
			.statusCode(404)
			.header("message", Matchers.notNullValue());			
		
	}
	
	@Test
	public void findPersonByID() {
		
		CreatePersonRequest paulo = new CreatePersonRequest.Builder()
				.withFirstName("Paulo")
				.withLastName("almeida")
				.withEmail("palmeida@hotmail.com")
				.withNickName("paulo")
				.withCity("Maringa")
				.withState("Parana")
				.withCountry("Brazil")
				.withZipCode("87020270")
				.build();
		
		String personID = createPerson(paulo);
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/persons/" + personID)
		.then()
			.statusCode(200)
			.body(Matchers.notNullValue());
		
	}
	
	@Test
	public void retrievePersonByInvalidId() {
		
		given().
			when()
				.get("/person/" + UUID.randomUUID())
			.then()
				.statusCode(404);
		
	}
	
	private String createPerson(CreatePersonRequest request) {
		
		String location = given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(request)
		.when()
		 	.post("/persons")
		.then()
			.statusCode(200)
			.extract().header("location");
		
		return location.replace("/persons/", "");
		
	}
	
	private CreatePersonRequest newCreatePersonRequest() {
		
		return new CreatePersonRequest.Builder()
				.withFirstName("Willian")
				.withLastName("Azevedo")
				.withEmail("willian-mga@hotmail.com")
				.withNickName("bili")
				.withCity("Maringa")
				.withState("Parana")
				.withCountry("Brazil")
				.withZipCode("87025640")
				.build();
		
	}	
	
}

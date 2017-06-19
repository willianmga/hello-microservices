package com.matera.hellomicroservices.rest;

import static com.jayway.restassured.RestAssured.given;

import java.util.UUID;

import org.hamcrest.Matchers;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;

public class PersonRSIT {

	//@BeforeClass
	public static void setup() {
		
		RestAssured.port = 9080;
		RestAssured.basePath = "";  // "/hellomicroservicesmiddle/";
		RestAssured.baseURI = "http://localhost";
		
	}

	//@Test
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
			.header("location", Matchers.notNullValue())
			.header("message", "Success");
		
	}

	//@Test
	public void createAndUpdatePersonOk() {
		
		// Creates the person
		CreatePersonRequest created = newCreatePersonRequest();
		
		String personID = given()
							.accept(ContentType.JSON)
							.contentType(ContentType.JSON)
							.body(created)
						.when()
							.post("/persons")
						.then()
							.statusCode(200)
							.contentType(ContentType.JSON)
							.extract().header("location");
		
		personID = personID.replace("/persons/", "");
			
		// updates the person
		
		CreatePersonRequest updated = new CreatePersonRequest.Builder()
				.withFirstName("Paulo")
				.withLastName("Almeida")
				.withEmail("palmeida@hotmail.com")
				.withNickName("paulo")
				.withCity(created.getCity())
				.withState(created.getState())
				.withCountry(created.getCountry())
				.withZipCode(created.getZipCode())
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
			.header("location", Matchers.notNullValue())
			.header("message", "Success");		
		
	}
	
	//@Test
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
	
	//@Test
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

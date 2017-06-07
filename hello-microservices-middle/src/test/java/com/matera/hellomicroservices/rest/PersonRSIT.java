package com.matera.hellomicroservices.rest;

import static com.jayway.restassured.RestAssured.given;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.PeopleResource;

public class PersonRSIT {

	private String insertedPersonLocation;
	
	@BeforeClass
	public static void setup() {
		
		RestAssured.port = 9292;
		RestAssured.basePath = "/hellomicroservicesmiddle/";
		RestAssured.baseURI = "http://localhost";
		
	}

	@Test
	public void makeSureApiIsUp() {
		
		given().when().get("/persons").then().statusCode(404);
		
	}
	
	@Test
	public void createPeopleOk() {
		
		CreatePersonRequest person1 = new CreatePersonRequest.Builder()
				.withFirstName("Willian")
				.withLastName("Azevedo")
				.withEmail("willian-mga@hotmail.com")
				.withNickName("bili")
				.withCity("Maringa")
				.withState("Parana")
				.withCountry("Brazil")
				.withZipCode("87025640")
				.build();
		
		System.out.println("Vou extrair o id");
		
		insertedPersonLocation = given()
									.accept("application/json")
									.contentType("application/json")
									.body(person1)
										.when()
											.post("/persons")
										.then()
											.statusCode(200)
//											.body("location", != null)
											.extract().path("location");
		
		System.out.println("ID da pessoa criada AGORA: " + insertedPersonLocation);
		
		CreatePersonRequest person2 = new CreatePersonRequest.Builder()
				.withFirstName("Camila")
				.withLastName("Vargas")
				.withEmail("willian-mga@hotmail.com")
				.withNickName("bili")
				.withCity("Maringa")
				.withState("Parana")
				.withCountry("Brazil")
				.withZipCode("87025640")
				.build();
		
		given()
			.accept("application/json")
			.contentType("application/json")
			.body(person2)
				.when()
					.post("/persons")
				.then()
					.statusCode(200);	
		
	}
	
	@Test
	public void retrieveAllPeopleOk() {
		
		PeopleResource people =  given()
									.when()
										.get("/persons")
										.as(PeopleResource.class);
		
		assertTrue(people.getPeopleResource().size() == 2);
		
	}
	
	@Test
	public void retrievePersonByValidId() {

		System.out.println("ID da pessoa criada: " + insertedPersonLocation);
		
		given()
			.when()
				.get(insertedPersonLocation)
			.then()
//				.body("firstName", equalTo("Willian"))
//				.body("email", equalTo("willian-mga@hotmail.com"))
				.statusCode(200);
		
	}
	
	@Test
	public void retrievePersonByInvalidId() {
		
		given().
			when()
				.get("/person/123")
			.then()
				.statusCode(404);
		
	}
	
}

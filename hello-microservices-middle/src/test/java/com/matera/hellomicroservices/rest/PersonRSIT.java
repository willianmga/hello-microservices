package com.matera.hellomicroservices.rest;

import static com.jayway.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;

public class PersonRSIT {

	private static String insertedPersonLocation;
	
	@BeforeClass
	public static void setup() {
		
		RestAssured.port = 9292;
		RestAssured.basePath = "/hellomicroservicesmiddle/";
		RestAssured.baseURI = "http://localhost";
		
	}

	@Before
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
		
		insertedPersonLocation = given()
									.accept(ContentType.JSON)
									.contentType(ContentType.JSON)
									.body(person1)
								.when()
									.post("/persons")
								.then()
									.statusCode(200)
									.contentType(ContentType.JSON)
									.extract().header("location");
		
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
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(person2)
		.when()
			.post("/persons")
		.then()
			.statusCode(200)
			.contentType(ContentType.JSON);	
		
	}
	
	@Test
	public void retrievePersonByValidId() {
	
		given()
			.when()
				.get(insertedPersonLocation)
			.then()
				.statusCode(200)	
				.contentType(ContentType.JSON)
//				.body("firstName", equalTo("Willian"))
//				.body("email", equalTo("willian-mga@hotmail.com"));
				;
		
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

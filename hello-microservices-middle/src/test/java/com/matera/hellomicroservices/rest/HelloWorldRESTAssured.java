package com.matera.hellomicroservices.rest;

import static com.jayway.restassured.RestAssured.given;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class HelloWorldRESTAssured {
	
	@BeforeClass
	public static void setup() {
		
		RestAssured.port = 9292;
		RestAssured.basePath = "hellomicroservicesmiddle";
		RestAssured.baseURI = "http://localhost";		
		
	}
	
	
	@Test
	public void makeSureApiIsUp() {
		
		System.out.println("Let's begin the test");
		given().when().get("/persons").then().statusCode(404);
		System.out.println("The test has passed successfully");
		
	}
		
	
	
	@Test
	public void makeSureGoogleIsUp() {

		given().when().get("www.ausdhausdhuasdhasudhasudhua.com.br").then().statusCode(200);
		
	}
	

}

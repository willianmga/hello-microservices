package com.matera.microservices.rest;

import java.util.UUID;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.matera.microservices.service.PersonService;
import com.sun.jersey.api.client.ClientResponse.Status;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class PersonRSTest {
	
	@Mock
	private PersonService personService;
	private PersonRS personRS;
	
	@Before
	public void before() {
		personRS = new PersonRS(personService);
	}
	
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
		
		CreatePersonResponse response = new CreatePersonResponse.Builder()
											.withID(UUID.randomUUID())
											.withMessage("Success")
											.build();
		
		Observable<CreatePersonResponse> observable = Observable.just(response);
		
		Mockito.when(personService.createPerson(person)).thenReturn(observable);
		
		Response methodResponse = personRS.createPerson(person);
		
		Assert.assertEquals(Status.OK.getStatusCode(), methodResponse.getStatus());
		
	}

}

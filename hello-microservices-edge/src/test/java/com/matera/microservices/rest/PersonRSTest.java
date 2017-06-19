package com.matera.microservices.rest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.matera.hellomicroservices.queries.PersonQuery;
import com.matera.microservices.service.PersonService;
import com.sun.jersey.api.client.ClientResponse.Status;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.AddressResource;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PeopleResource;
import matera.com.hellomicroservices.core.responses.PersonResource;
import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class PersonRSTest {
	
	@Mock
	private PersonService personService;
	
	@InjectMocks
	private PersonRS personRS;
	
	private static final String RANDOM_UUID = UUID.randomUUID().toString();
	
	/**
	 * Test if ({@link PersonRS#createPerson(CreatePersonRequest)} will 
	 * return an Observable of {@link CreatePersonResponse}
	 * 
	 * @throws Exception
	 */
	@Test
	public void createPersonOk() throws Exception {
		
		CreatePersonRequest person = newCreatePersonRequest();		
		Observable<CreatePersonResponse> observable = Observable.just(newCreatePersonResponse());
		
		Mockito.when(personService.createPerson(person)).thenReturn(observable);
		
		Response response = personRS.createPerson(person);
		
		CreatePersonResponse created = (CreatePersonResponse) response.getEntity(); 
		
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals(RANDOM_UUID, created.getId().toString());
		assertEquals("Success", created.getMessage());
		
	}

	/**
	 * Tests if {@link PersonRS#findPersonByUUID(String)} will 
	 * return an Observable of PersonResource and status 200 when 
	 * finding for a valid uuid
	 * 
	 * @throws Exception
	 */
	@Test
	public void findPersonByUUIDOk() throws Exception {
		
		Observable<PersonResource> observable = Observable.just(newPersonResource(RANDOM_UUID));
		
		Mockito.when(personService.findPersonByUUID(RANDOM_UUID)).thenReturn(observable);
		
		Response response = personRS.findPersonByUUID(RANDOM_UUID);
		
		PersonResource personFound = (PersonResource) response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(RANDOM_UUID, personFound.getUuid().toString());
		assertEquals("Willian", personFound.getFirstName());
		assertEquals("willian-mga@hotmail.com", personFound.getEmail());
		
	}
	
	/**
	 * Tests if {@link PersonRS#findPersonByUUID(String)} will
	 * return 404 status when searching by an "invalid" or "inexistent" uuid
	 * 
	 * @throws Exception
	 */
	@Test
	public void findPersonByInvalidUUID() throws Exception {
		
		Observable<PersonResource> observable = Observable.empty();
		
		Mockito.when(personService.findPersonByUUID(RANDOM_UUID))
			   .thenReturn(observable);
		
		Response response = personRS.findPersonByUUID(RANDOM_UUID);
		
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		
	}
	
	/**
	 * Tests if {@link PersonRS#findAllPeople(String, String, String)} will
	 * return 200 status and Observable of PeopleResource when finding for "valid" params
	 * 
	 * @throws Exception
	 */
	@Test 
	public void findPersonByQueryOk() throws Exception {
		
		Observable<PeopleResource> observable = Observable.just(newPeopleResource());
			
		Mockito.when(personService.findAllPeople(Mockito.any(PersonQuery.class)))
		       .thenReturn(observable);
		
		Response response = personRS.findAllPeople("Willian", "Azevedo", "");
		
		PeopleResource people = (PeopleResource) response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(1, people.getPeopleResource().size());
		assertEquals(RANDOM_UUID, people.getPeopleResource().get(0).getUuid().toString());
		assertEquals("Willian", people.getPeopleResource().get(0).getFirstName());
		
	}
	
	/**
	 * Tests if {@link PersonRS#findAllPeople(String, String, String)} will
	 * return 404 status when finding for "invalid" and "inexistent" params
	 * 
	 * @throws Exception
	 */
	@Test 
	public void findPersonByQueryNotFound() throws Exception {
		
		Observable<PeopleResource> observable = Observable.empty();
			
		Mockito.doReturn(observable)
			   .when(personService)
			   .findAllPeople(Mockito.any(PersonQuery.class));
		
		Response response = personRS.findAllPeople("Willian", "Azevedo", "");
		
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

	}	

	private PersonResource newPersonResource(String uuid) {
		
		AddressResource address = new AddressResource.Builder()
				.withCity("Maringá")
				.withState("Parana")
				.withCountry("Brasil")
				.withZipCode("87025640")
				.build();

		return new PersonResource.Builder()
				.withFirstName("Willian")
				.withLastName("Azevedo")
				.withNickName("willianmga")
				.withEmail("willian-mga@hotmail.com")
				.withUUID(UUID.fromString(uuid))
				.withAddress(address)
				.build();
		
	}
	
	public PeopleResource newPeopleResource() {
		
		List<PersonResource> personList = new ArrayList<PersonResource>();
		personList.add(newPersonResource(RANDOM_UUID));
		
		return new PeopleResource.Builder()
				.withPeople(personList)
				.build();
		
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
	
	private CreatePersonResponse newCreatePersonResponse() {
		
		return new CreatePersonResponse.Builder()
				.withID(UUID.fromString(RANDOM_UUID))
				.withMessage("Success")
				.build();
		
	}	
	
}

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
import com.netflix.hystrix.exception.HystrixRuntimeException;
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
	 * Test if ({@link PersonRS#create(CreatePersonRequest)} will 
	 * return an Observable of {@link CreatePersonResponse}
	 * 
	 * @throws Exception
	 */
	@Test
	public void createPersonOk() throws Exception {
		
		CreatePersonRequest person = newCreatePersonRequest();		
		Observable<CreatePersonResponse> observable = Observable.just(newCreatePersonResponse());
		
		Mockito.when(personService.create(person)).thenReturn(observable);
		
		Response response = personRS.create(person);
		
		CreatePersonResponse created = (CreatePersonResponse) response.getEntity(); 
		
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals(RANDOM_UUID, created.getId().toString());
		assertEquals("Success", created.getMessage());
		
	}
	
	/**
	 * Tests if ({@link PersonRS#update(String, CreatePersonRequest)} will return 503
	 * when trying to update a person and the service is not avaliable
	 * 
	 * @throws Exception
	 */
	@Test
	public void createPersonServiceUnavaliable() throws Exception {
		
		CreatePersonRequest person = newCreatePersonRequest();		
		
		Mockito.doReturn(Observable.empty())
			.when(personService)
			.update(Mockito.anyString(), Mockito.any(CreatePersonRequest.class));
		
		Response response = personRS.update(RANDOM_UUID, person);
		
		assertEquals(503, response.getStatus());
		
	}
	
	/**
	 * Tests if ({@link PersonRS#update(String, CreatePersonRequest)} will return an observable 
	 * of CreatePersonResponse when updating a person
	 * 
	 * @throws Exception
	 */
	@Test
	public void updatePersonOk() throws Exception {
		
		CreatePersonRequest person = newCreatePersonRequest();
		Observable<CreatePersonResponse> observable = Observable.just(newCreatePersonResponse());
		
		Mockito.doReturn(observable)
			.when(personService)
			.update(RANDOM_UUID, person);
		
		Response response = personRS.update(RANDOM_UUID, person);
		
		CreatePersonResponse updated = (CreatePersonResponse) response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(RANDOM_UUID, updated.getId().toString());
		assertEquals("Success", updated.getMessage());
		
	}
	
	/**
	 * Tests if ({@link PersonRS#delete(String)} will return 200 status
	 * when deleting a person through a valid id
	 * 
	 * @throws Exception
	 */
	@Test
	public void deletePersonOk() throws Exception {
		
		Observable<Integer> observable = Observable.just(Status.OK.getStatusCode());
		
		Mockito.doReturn(observable)
			.when(personService)
			.delete(Mockito.anyString());
		
		Response response = personRS.delete(RANDOM_UUID);
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		
	}
	
	/**
	 * Tests if ({@link PersonRS#delete(String)} will return 404 status
	 * when trying to delete a person through a inexistent id
	 * 
	 * @throws Exception
	 */
	@Test
	public void deletePersonThroughInexistentID() throws Exception {
		
		Observable<Integer> observable = Observable.just(Status.NOT_FOUND.getStatusCode());
		
		Mockito.doReturn(observable)		
			.when(personService)
			.delete(Mockito.anyString());
		
		Response response = personRS.delete(RANDOM_UUID);
		
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		
	}
	
	/**
	 * Tests if ({@link PersonRS#delete(String)} will return 400 status
	 * when trying to delete a person through a invalid id
	 * 
	 * @throws Exception
	 */
	@Test
	public void deletePersonThroughInvalidID() throws Exception {
		
		Observable<Integer> observable = Observable.just(Status.BAD_REQUEST.getStatusCode());
		
		Mockito.doReturn(observable)				
			.when(personService)
			.delete(Mockito.anyString());
		
		Response response = personRS.delete(RANDOM_UUID);
		
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		
	}
	
	/**
	 * Tests if ({@link PersonRS#delete(String)} will return 503 status
	 * when trying to access middle service
	 * 
	 * @throws Exception
	 */
	@Test
	public void deletePersonFailsWhenHitsMiddle() throws Exception {
		
		Mockito.doThrow(HystrixRuntimeException.class)
			.when(personService)
			.delete(Mockito.anyString());
		
		Response response = personRS.delete(RANDOM_UUID);
		
		assertEquals(Status.SERVICE_UNAVAILABLE.getStatusCode(), response.getStatus());
		
	}		

	/**
	 * Tests if {@link PersonRS#findByUUID(String)} will 
	 * return an Observable of PersonResource and status 200 when 
	 * finding for a valid uuid
	 * 
	 * @throws Exception
	 */
	@Test
	public void findPersonByUUIDOk() throws Exception {
		
		Observable<PersonResource> observable = Observable.just(newPersonResource(RANDOM_UUID));
		
		Mockito.when(personService.findByUUID(RANDOM_UUID)).thenReturn(observable);
		
		Response response = personRS.findByUUID(RANDOM_UUID);
		
		PersonResource personFound = (PersonResource) response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(RANDOM_UUID, personFound.getUuid().toString());
		assertEquals("Willian", personFound.getFirstName());
		assertEquals("willian-mga@hotmail.com", personFound.getEmail());
		
	}
	
	/**
	 * Tests if {@link PersonRS#findByUUID(String)} will
	 * return 404 status when searching by an "invalid" or "inexistent" uuid
	 * 
	 * @throws Exception
	 */
	@Test
	public void findPersonByInvalidUUID() throws Exception {
		
		Observable<PersonResource> observable = Observable.empty();
		
		Mockito.when(personService.findByUUID(RANDOM_UUID))
			   .thenReturn(observable);
		
		Response response = personRS.findByUUID(RANDOM_UUID);
		
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		
	}
	
	/**
	 * Tests if {@link PersonRS#findAll(String, String, String)} will
	 * return 200 status and Observable of PeopleResource when finding for "valid" params
	 * 
	 * @throws Exception
	 */
	@Test 
	public void findPersonByQueryOk() throws Exception {
		
		Observable<PeopleResource> observable = Observable.just(newPeopleResource());
			
		Mockito.when(personService.findAll(Mockito.any(PersonQuery.class)))
		       .thenReturn(observable);
		
		Response response = personRS.findAll("Willian", "Azevedo", "");
		
		PeopleResource people = (PeopleResource) response.getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(1, people.getPeopleResource().size());
		assertEquals(RANDOM_UUID, people.getPeopleResource().get(0).getUuid().toString());
		assertEquals("Willian", people.getPeopleResource().get(0).getFirstName());
		
	}
	
	/**
	 * Tests if {@link PersonRS#findAll(String, String, String)} will
	 * return 404 status when finding for "invalid" and "inexistent" params
	 * 
	 * @throws Exception
	 */
	@Test 
	public void findPersonByQueryNotFound() throws Exception {
		
		Observable<PeopleResource> observable = Observable.empty();
			
		Mockito.doReturn(observable)
			   .when(personService)
			   .findAll(Mockito.any(PersonQuery.class));
		
		Response response = personRS.findAll("Willian", "Azevedo", "");
		
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

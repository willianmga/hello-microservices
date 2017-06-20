package com.matera.microservices.api.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.hellomicroservices.queries.PersonQuery;
import com.matera.microservices.api.PersonClient;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import matera.com.hellomicroservices.core.config.HelloMicroservicesObjectMapperProvider;
import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.AddressResource;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PeopleResource;
import matera.com.hellomicroservices.core.responses.PersonResource;
import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class HystrixPersonClientTest {

	private final UUID RANDOM_UUID = UUID.randomUUID();
	
	@Mock
	private HttpClient httpClient;
	private ObjectMapper mapper;
	private HystrixPersonClient hystrixClient;
	
	@Before
	public void init() {
		
		mapper = new HelloMicroservicesObjectMapperProvider().get();
		
		hystrixClient = new HystrixPersonClient(httpClient, mapper);
		
	}
	
	/**
	 * Tests if {@link PersonClient#create(CreatePersonRequest)} will call
	 * middle service and return {@link Observable} of the response
	 */
	@Test
	public void createPersonOk() throws Exception {
		
		Mockito
			.doReturn(httpSuccessCreatePersonResponse())
			.when(httpClient)
			.execute(Mockito.any(HttpPost.class));
		
		CreatePersonRequest person = newCreatePersonRequest();
		CreatePersonResponse personResponse = hystrixClient.create(person).toBlocking().single();

		assertEquals("Success", personResponse.getMessage());
		assertThat(personResponse.getId(), Matchers.notNullValue());
		
	}
	
	/**
	 * Tests if ({@link HystrixPersonClient#create(CreatePersonRequest)} will return 404
	 * from middle when the service is not up
	 */
	@Test
	public void createPersonFailWhenHitsTheMiddle() throws Exception {
		
		Mockito.doReturn(httpNotFoundResponse())
			   .when(httpClient)
			   .execute(Mockito.any(HttpPost.class));
		
		hystrixClient.create(newCreatePersonRequest()).toBlocking().single();

	}
	
	/**
	 * Tests if ({@link HystrixPersonClient#update(CreatePersonRequest, UUID)} will return 200
	 * from middle when updating an existing person 
	 */
	@Test
	public void updatePersonOk() throws Exception {
		
		Mockito.doReturn(httpSuccessCreatePersonResponse())
			.when(httpClient)
			.execute(Mockito.any(HttpPut.class));
			
		CreatePersonRequest person = newCreatePersonRequest();
		
		CreatePersonResponse response = hystrixClient.update(RANDOM_UUID, person).toBlocking().single();
		
		assertEquals("Success", response.getMessage());
		assertEquals(RANDOM_UUID, response.getId());
		
	}
	
	/**
	 * Tests if {@link HystrixPersonClient#update(CreatePersonRequest, UUID)} will return 404
	 * from middle when no person was found by given id
	 * 
	 * @throws Exception
	 */
	@Test
	public void updatePersonFails() throws Exception {

		Mockito.doReturn(httpNotFoundResponse())
			.when(httpClient)
			.execute(Mockito.any(HttpPut.class));
		
		hystrixClient.update(RANDOM_UUID, newCreatePersonRequest());
		
	}
	/**
	 * Tests if {@link HystrixPersonClient#delete(UUID)} will return 200 status
	 * when deleting an exiting person
	 * 
	 * @throws Exception
	 */
	@Test
	public void deletePersonOk() throws Exception {
		
		Mockito.doReturn(newHttpSuccessResponse())
			.when(httpClient)
			.execute(Mockito.any(HttpDelete.class));
		
		hystrixClient.delete(RANDOM_UUID).toBlocking().single();
		
	}
	
	/**
	 * Tests if {@link HystrixPersonClient#delete(UUID)} will return {@link HystrixRuntimeException}
	 * when trying do delete a person through an inexistent id
	 * 
	 * @throws Exception
	 */
	@Test(expected = HystrixRuntimeException.class)
	public void deletePersonWithInexistentID() throws Exception {
		
		Mockito.doReturn(httpNotFoundResponse())
			.when(httpClient)
			.execute(Mockito.any(HttpDelete.class));
		
		hystrixClient.delete(RANDOM_UUID).toBlocking().single();
		
	}
	
	/**
	 * Tests if ({@link HystrixPersonClient#delete(UUID)} will return ({@link HystrixRuntimeException}
	 * when trying do delete a person through an invalid id
	 * 
	 * @throws Exception
	 */
	@Test(expected = HystrixRuntimeException.class)
	public void deletePersonWithInvalidID() throws Exception {

		Mockito.doReturn(httpBadRequestResponse())
			.when(httpClient)
			.execute(Mockito.any(HttpDelete.class));
	
		hystrixClient.delete(RANDOM_UUID).toBlocking().single();		
		
	}
	
	/**
	 * Tests if ({@link HystrixPersonClient#searchByUUID(UUID)} returns a specific person
	 * when finding a "valid" person through its id
	 */
	@Test
	public void findPersonByValidUUID() throws Exception {
		
		Mockito.doReturn(httpSuccessFindPersonResponse())
			   .when(httpClient)
			   .execute(Mockito.any(HttpGet.class));
		
		PersonResource resource = hystrixClient.searchByUUID(RANDOM_UUID).toBlocking().single();
		
		assertEquals(RANDOM_UUID, resource.getUuid());
		assertEquals("Willian", resource.getFirstName());
		assertEquals("87025640", resource.getAddress().getZipCode());
		
	}
	
	/**
	 * Tests if ({@link HystrixPersonClient#searchByUUID(UUID)} will return 404 status
	 * when finding a "invalid" person id 
	 */
	@Test
	public void findPersonByInvalidUUID() throws Exception {
		
		Mockito.doReturn(httpNotFoundResponse())
			   .when(httpClient)
			   .execute(Mockito.any(HttpGet.class));
		
		hystrixClient.searchByUUID(RANDOM_UUID).toBlocking().single();
		
	}
	
	/**
	 * Tests if {@link HystrixPersonClient#searchBy(com.matera.hellomicroservices.queries.PersonQuery)} will return
	 * the matching people
	 * 
	 * @throws Exception
	 */
	@Test
	public void findPeopleByQueryOk() throws Exception {
		
		Mockito.doReturn(httpSuccessFindPeopleResponse())
		   .when(httpClient)
		   .execute(Mockito.any(HttpGet.class));
		
		PersonQuery query = new PersonQuery.Builder()
				.withFirstName("Willian")
				.withLastName("Azevedo")
				.withZipCode("87025640")
				.build();
		
		PeopleResource people = hystrixClient.searchBy(query).toBlocking().single();
		assertEquals(1, people.getPeopleResource().size());
		
		PersonResource person = people.getPeopleResource().get(0);
		assertEquals("Willian", person.getFirstName());
		assertEquals("Azevedo", person.getLastName());
		assertEquals("87025640", person.getAddress().getZipCode());
		
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

	public CreatePersonResponse newCreatePersonResponse() {

		return new CreatePersonResponse.Builder()
						.withID(RANDOM_UUID)
						.withMessage("Success")
						.build();

	}	
	
	public PersonResource newPersonResource() {
		
		AddressResource address = new AddressResource.Builder()
				.withCity("Maringá")
				.withState("Paraná")
				.withCountry("Brazil")
				.withZipCode("87025640")
				.build();
		
		return new PersonResource.Builder()
			.withUUID(RANDOM_UUID)
			.withFirstName("Willian")
			.withLastName("Azevedo")
			.withEmail("willian-mga@hotmail.com")
			.withNickName("willianmga")
			.withAddress(address)
			.build();				
		
	}
	
	public PeopleResource newPeopleResource() {
		
		List<PersonResource> personList = new ArrayList<PersonResource>();
		personList.add(newPersonResource());
		
		return new PeopleResource.Builder()
				.withPeople(personList)
				.build();
		
	}
	
	private HttpResponse httpSuccessCreatePersonResponse() throws Exception {
		
		BasicHttpEntity entity = newBasicHttpEntity();
		entity.setContent(new ByteArrayInputStream(mapper.writeValueAsBytes(newCreatePersonResponse())));
		
		HttpResponse response = newHttpSuccess();
		response.setEntity(entity);
		
		return response;
		
	}
	
	private HttpResponse httpSuccessFindPersonResponse() throws Exception {
		
		BasicHttpEntity entity = newBasicHttpEntity();
		entity.setContent(new ByteArrayInputStream(mapper.writeValueAsBytes(newPersonResource())));
		
		HttpResponse response = newHttpSuccess();
		response.setEntity(entity);
		
		return response;
		
	}
	
	private HttpResponse httpSuccessFindPeopleResponse() throws Exception {
		
		BasicHttpEntity entity = newBasicHttpEntity();
		entity.setContent(new ByteArrayInputStream(mapper.writeValueAsBytes(newPeopleResource())));
		
		HttpResponse response = newHttpSuccess();
		response.setEntity(entity);
		
		return response;
		
	}	
	
	private HttpResponse newHttpSuccessResponse() {
		
		HttpResponse response = newHttpSuccess();
		response.setEntity(newBasicHttpEntity());
		
		return response;		
		
	}
	
	private HttpResponse newHttpSuccess() {
		
		return new BasicHttpResponse(new BasicStatusLine(
										new ProtocolVersion("HTTP", 4, 0), 200, null));
		
	}
	
	private HttpResponse httpNotFoundResponse() throws Exception {
		
		return new BasicHttpResponse(new BasicStatusLine(
										new ProtocolVersion("HTTP", 4, 0), 404, null));
		
	}
	
	private HttpResponse httpBadRequestResponse() {
		
		return new BasicHttpResponse(new BasicStatusLine(
										new ProtocolVersion("HTTP", 4, 0), 400, null));
		
	}	
	
	private BasicHttpEntity newBasicHttpEntity() {
		
		BasicHttpEntity entity = new BasicHttpEntity();
		entity.setContentType("application/json");		
		return entity;
		
	}

}

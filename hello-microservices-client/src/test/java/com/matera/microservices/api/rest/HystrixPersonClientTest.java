package com.matera.microservices.api.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.microservices.api.PersonClient;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.AddressResource;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PersonResource;
import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class HystrixPersonClientTest {

	@Mock
	private HttpClient httpClient;
	
	@Spy
	private ObjectMapper mapper = new ObjectMapper();
	
	@InjectMocks
	private HystrixPersonClient hystrixClient;
	
	private UUID uuid = UUID.randomUUID();
	
	/**
	 * Tests if {@link PersonClient#createPerson(CreatePersonRequest)} will call
	 * middle service and return {@link Observable} of the response
	 */
	@Test
	public void createPersonOk() throws Exception {
		
		Mockito
			.doReturn(httpSuccessCreatePersonResponse())
			.when(httpClient)
			.execute(Mockito.any(HttpPost.class));
		
		CreatePersonRequest person = newCreatePersonRequest();
		CreatePersonResponse personResponse = hystrixClient.createPerson(person).toBlocking().single();

		assertEquals("Success", personResponse.getMessage());
		assertThat(personResponse.getId(), Matchers.notNullValue());
		
	}
	
	/**
	 * Tests if ({@link HystrixPersonClient#createPerson(CreatePersonRequest)} will return 404
	 * from middle when the service is not up
	 */
	@Test
	public void createPersonFailWhenHitsTheMiddle() throws Exception {
		
		Mockito.doReturn(httpNotFoundResponse())
			   .when(httpClient)
			   .execute(Mockito.any(HttpPost.class));
		
		hystrixClient.createPerson(newCreatePersonRequest()).toBlocking().single();

	}
	
	/**
	 * Tests if ({@link HystrixPersonClient#findPersonByUUID(UUID)} returns a specific person
	 * when finding a "valid" person through its id
	 */
	@Test
	public void findPersonByValidUUID() throws Exception {
		
		Mockito.doReturn(httpSuccessFindPersonResponse())
			   .when(httpClient)
			   .execute(Mockito.any(HttpGet.class));
		
		PersonResource resource = hystrixClient.findPersonByUUID(uuid).toBlocking().single();
		
		assertEquals(uuid, resource.getUuid());
		assertEquals("Willian", resource.getFirstName());
		assertEquals("87053070", resource.getAddress().getZipCode());
		
	}
	/**
	 * Tests if ({@link HystrixPersonClient#findPersonByUUID(UUID)} will return 404 status
	 * when finding a "invalid" person id 
	 */
	@Test
	public void findPersonByInvalidUUID() throws Exception {
		
		Mockito.doReturn(httpNotFoundResponse())
			   .when(httpClient)
			   .execute(Mockito.any(HttpGet.class));
		
		hystrixClient.findPersonByUUID(uuid).toBlocking().single();
		
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
						.withID(UUID.randomUUID())
						.withMessage("Success")
						.build();

	}	
	
	public PersonResource newPersonResource() {
		
		AddressResource address = new AddressResource.Builder()
				.withCity("Maringá")
				.withState("Paraná")
				.withCountry("Brazil")
				.withZipCode("87053070")
				.build();
		
		return new PersonResource.Builder()
			.withUUID(uuid)
			.withFirstName("Willian")
			.withLastName("Azevedo")
			.withEmail("willian-mga@hotmail.com")
			.withNickName("willianmga")
			.withAddress(address)
			.build();				
		
	}
	
	private HttpResponse httpNotFoundResponse() throws Exception {
		
		HttpResponse response = 
				new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("HTTP", 4, 0), 404, null));
		
		return response;
		
	}
	
	private HttpResponse httpSuccessCreatePersonResponse() throws Exception {
		
		BasicHttpEntity entity = new BasicHttpEntity();
		entity.setContentType("application/json");
		entity.setContent(new ByteArrayInputStream(mapper.writeValueAsBytes(newCreatePersonResponse())));
		
		HttpResponse response = newHttpSuccess();
		response.setEntity(entity);
		
		return response;
		
	}
	
	private HttpResponse httpSuccessFindPersonResponse() throws Exception {
		
		BasicHttpEntity entity = new BasicHttpEntity();
		entity.setContentType("application/json");
		entity.setContent(new ByteArrayInputStream(mapper.writeValueAsBytes(newPersonResource())));
		
		HttpResponse response = newHttpSuccess();
		response.setEntity(entity);
		
		return response;
		
	}	
	
	private HttpResponse newHttpSuccess() {
		return new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("HTTP", 4, 0), 200, null));
	}

}

package com.matera.microservices.api.command;

import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.microservices.config.HelloMicroservicesGroupKey;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;

import matera.com.hellomicroservices.core.responses.PersonResource;

public class PersonFindByIdCommand extends HystrixCommand<PersonResource> {

	private static final HystrixCommand.Setter SETTER;
	
	private static final DynamicStringProperty HELLO_MIDDLE_HOST;
	
	private HttpClient client;
	private ObjectMapper mapper;
	private UUID personID;	
	
	static {
		
		DynamicPropertyFactory config = DynamicPropertyFactory.getInstance();                                  
		HELLO_MIDDLE_HOST = config.getStringProperty("hellomicroservices.middle.host", 
													 "http://hellomicroservicesmiddle:8080/hellomicroservicesmiddle/persons/{id}");
		
		SETTER = Setter.withGroupKey(HelloMicroservicesGroupKey.MIDDLE)
				.andCommandKey(HystrixCommandKey.Factory.asKey(PersonFindByIdCommand.class.getSimpleName()));		
		
	}	

	public PersonFindByIdCommand(HttpClient client, ObjectMapper mapper, UUID personID) {
		
		super(SETTER);
		
		this.client = client;
		this.mapper = mapper;
		this.personID = personID;
		
	}

	@Override
	protected PersonResource run() throws Exception {
		
		URI uri = UriBuilder.fromPath(HELLO_MIDDLE_HOST.get()).build(personID.toString());
		
		HttpGet request = new HttpGet(uri);
		request.setHeader("Accept", "application/json");
		
		try {
		
			HttpResponse response = client.execute(request);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				try (InputStream is = response.getEntity().getContent()) {
					return mapper.readValue(is, PersonResource.class);
				}
			}
			
			return null;
		
		} catch(Exception e) {
			
			return null;
			
		}
		
	}

}

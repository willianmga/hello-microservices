package com.matera.microservices.api.command;

import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.microservices.config.HelloMicroservicesGroupKey;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;

public class PersonUpdateCommand extends HystrixCommand<CreatePersonResponse> {
	
	private static final HystrixCommand.Setter SETTER;
	private static final DynamicStringProperty HELLO_MIDDLE_HOST;	

	private final HttpClient client;
	private final ObjectMapper mapper;
	private final CreatePersonRequest person;
	private final UUID uuid;
	
	static {
		
		DynamicPropertyFactory config = DynamicPropertyFactory.getInstance();                                  
		HELLO_MIDDLE_HOST = config.getStringProperty("hellomicroservices.middle.host", 
													 "http://hellomicroservicesmiddle:8080/hellomicroservicesmiddle/persons/{id}");
		
		SETTER = Setter.withGroupKey(HelloMicroservicesGroupKey.MIDDLE)
				.andCommandKey(HystrixCommandKey.Factory.asKey(PersonUpdateCommand.class.getSimpleName()));		
		
	}	

	public PersonUpdateCommand(HttpClient client, ObjectMapper mapper, UUID uuid, CreatePersonRequest person) {
	
		super(SETTER);
		
		this.client = client;
		this.mapper = mapper;
		this.uuid = uuid;
		this.person = person;
		
	}

	public CreatePersonResponse run() throws Exception {
		
		URI uri = UriBuilder.fromPath(HELLO_MIDDLE_HOST.get()).build(uuid);
		
		HttpPut request = new HttpPut(uri);
		request.setHeader("Content-Type", "application/json");
		request.setHeader("Accept", "application/json");
		request.setEntity(new StringEntity(mapper.writeValueAsString(person)));
		
		try {
		
			HttpResponse response = client.execute(request);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				try (InputStream is = response.getEntity().getContent()) {
					return mapper.readValue(is, CreatePersonResponse.class);
				}
			}
			
			return null;
			
		} catch(Exception e) {
			
			return null;
			
		}
	}

}

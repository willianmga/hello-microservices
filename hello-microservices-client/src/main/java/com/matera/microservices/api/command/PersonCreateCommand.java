package com.matera.microservices.api.command;

import java.io.InputStream;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.microservices.config.HelloMicroservicesGroupKey;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;

public class PersonCreateCommand extends HystrixCommand<CreatePersonResponse> {

	private static final HystrixCommand.Setter SETTER;
	
	private static final DynamicStringProperty HELLO_MIDDLE_HOST;	
	
	private final HttpClient clientProvider;
	private final ObjectMapper mapper;
	private final CreatePersonRequest createRequest;	
	
	static {
		
		DynamicPropertyFactory config = DynamicPropertyFactory.getInstance();                                  
		HELLO_MIDDLE_HOST = config.getStringProperty("hellomicroservices.middle.host", "http://hellomicroservicesmiddle:8080/hellomicroservicesmiddle/persons");
		
		SETTER = Setter.withGroupKey(HelloMicroservicesGroupKey.MIDDLE)
				.andCommandKey(HystrixCommandKey.Factory.asKey(PersonCreateCommand.class.getSimpleName()));		
		
	}
	
	public PersonCreateCommand(HttpClient clientProvider, final ObjectMapper mapper, final CreatePersonRequest request) {
		
		super(SETTER);
		
		this.clientProvider = clientProvider;
		this.mapper = mapper;
		this.createRequest = request;
		
	}

	@Override
	protected CreatePersonResponse run() throws Exception {
		
		URI uri = UriBuilder.fromPath(HELLO_MIDDLE_HOST.get()).build();
		
		HttpPost request = new HttpPost(uri);
		request.setHeader("Content-Type", "application/json");
		request.setHeader("Accept", "application/json");
		request.setEntity(new StringEntity(mapper.writeValueAsString(createRequest)));
		
		HttpResponse response = clientProvider.execute(request);
		
		if (response.getStatusLine().getStatusCode() == 200) {
			try (InputStream is = response.getEntity().getContent()) {
				return mapper.readValue(is, CreatePersonResponse.class);
			}
		}
		
		return null;
	}

}

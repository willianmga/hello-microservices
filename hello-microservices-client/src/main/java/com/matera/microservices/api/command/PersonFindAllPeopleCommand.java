package com.matera.microservices.api.command;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.InputStream;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.hellomicroservices.queries.PersonQuery;
import com.matera.microservices.config.HelloMicroservicesGroupKey;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;

import matera.com.hellomicroservices.core.responses.PeopleResource;

public class PersonFindAllPeopleCommand extends HystrixCommand<PeopleResource> {
	
	private static final HystrixCommand.Setter SETTER;
	
	private static final DynamicStringProperty HELLO_MIDDLE_HOST;	
	
	private HttpClient client;
	private ObjectMapper mapper;
	private PersonQuery personQuery;
	
	static {
		
		DynamicPropertyFactory config = DynamicPropertyFactory.getInstance();                                  
		HELLO_MIDDLE_HOST = config.getStringProperty("hellomicroservices.middle.host", "http://hellomicroservicesmiddle:8080/hellomicroservicesmiddle/persons");
		
		SETTER = Setter.withGroupKey(HelloMicroservicesGroupKey.MIDDLE)
				.andCommandKey(HystrixCommandKey.Factory.asKey(PersonFindAllPeopleCommand.class.getSimpleName()));		
		
	}	

	public PersonFindAllPeopleCommand(HttpClient client, ObjectMapper mapper, PersonQuery personQuery) {
		
		super(SETTER);
		
		this.client = client;
		this.mapper = mapper;
		this.personQuery = personQuery;
		
	}

	@Override
	protected PeopleResource run() throws Exception {
		
		UriBuilder uri = UriBuilder.fromPath(HELLO_MIDDLE_HOST.get());
		
		if (isNotBlank(personQuery.getFirstName())) {
			uri.queryParam("firstName", personQuery.getFirstName());
		}
		
		if (isNotBlank(personQuery.getLastName())) {
			uri.queryParam("lastName", personQuery.getLastName());
		}
		
		if (isNotBlank(personQuery.getZipCode())) {
			uri.queryParam("zipCode", personQuery.getZipCode());
		}	
		
		HttpGet request = new HttpGet(uri.build());
		request.setHeader("Accept", "application/json");
		
		System.out.println("Http get para uri: " + uri.build().toString());
		
		try {
		
			HttpResponse response = client.execute(request);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				try (InputStream is = response.getEntity().getContent()) {
					return mapper.readValue(is, PeopleResource.class);
				}
			}
			
			return null;
		
		} catch(Exception e) {
			
			return null;
			
		}
		
	}

}

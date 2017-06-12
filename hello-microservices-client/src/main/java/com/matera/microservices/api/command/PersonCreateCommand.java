package com.matera.microservices.api.command;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.matera.microservices.config.HelloMicroservicesGroupKey;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpRequest.Verb;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.niws.client.http.RestClient;
	   
import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;

@SuppressWarnings("deprecation")
public class PersonCreateCommand extends HystrixCommand<CreatePersonResponse> {

	private static final HystrixCommand.Setter SETTER = Setter.withGroupKey(HelloMicroservicesGroupKey.MIDDLE)
			.andCommandKey(HystrixCommandKey.Factory.asKey(PersonCreateCommand.class.getSimpleName()));
	
	private static final String DEFAULT_URL = "hellomicroservicesmiddle/persons";
	private static final String URL = "crudmicroservices.persons.create.url";
	
	private final RestClient client;
	private final ObjectMapper mapper;
	private final CreatePersonRequest createRequest;

	public PersonCreateCommand(final RestClient client, final ObjectMapper mapper, final CreatePersonRequest request) {
		
		super(SETTER);
		
		this.client = client;
		this.mapper = mapper;
		this.createRequest = request;
		
	}

	@Override
	protected CreatePersonResponse run() throws Exception {
		
		String personCreateURL = DynamicPropertyFactory.getInstance().getStringProperty(URL, DEFAULT_URL).get();
		
		URI uri = UriBuilder.fromPath(personCreateURL).build();
		
		HttpRequest request = HttpRequest.newBuilder()
						.verb(Verb.POST)
							.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
								.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
									.uri(uri)
										.entity(createRequest)
											.build();
		try (HttpResponse response = client.executeWithLoadBalancer(request)) {
			return mapper.readValue(response.getInputStream(), CreatePersonResponse.class);
		}
		
	}

}

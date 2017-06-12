package com.matera.microservices.config;

import com.google.inject.AbstractModule;

import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.matera.microservices.api.PersonClient;
import com.matera.microservices.api.rest.HystrixPersonClient;
import com.netflix.client.ClientFactory;
import com.netflix.niws.client.http.RestClient;

@SuppressWarnings("deprecation")
public class HelloMicroservicesClientModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(PersonClient.class).to(HystrixPersonClient.class).in(Scopes.SINGLETON);;
		
	}
	
	@Provides
	@Singleton
	@Named("ValidationRestClient")
	public RestClient getRestClient() {
		
		return (RestClient) ClientFactory.getNamedClient("hellomicroservicesmiddle");
		
	}
	
}

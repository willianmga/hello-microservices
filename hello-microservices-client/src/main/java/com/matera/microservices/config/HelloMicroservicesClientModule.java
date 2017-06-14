package com.matera.microservices.config;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.matera.microservices.api.PersonClient;
import com.matera.microservices.api.rest.HystrixPersonClient;

public class HelloMicroservicesClientModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(PersonClient.class).to(HystrixPersonClient.class).in(Scopes.SINGLETON);
		
	}
}

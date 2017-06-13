package com.matera.microservices.config;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.matera.microservices.service.PersonService;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(PersonService.class).in(Scopes.SINGLETON);

	}

}

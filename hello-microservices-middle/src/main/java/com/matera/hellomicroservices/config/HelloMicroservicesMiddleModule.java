package com.matera.hellomicroservices.config;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.matera.hellomicroservices.service.PersonService;

public class HelloMicroservicesMiddleModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PersonService.class).in(Scopes.SINGLETON);
	}

}

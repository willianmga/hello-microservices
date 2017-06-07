package com.matera.hellomicroservices.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class RestModule extends JerseyServletModule {
	
	@Override
	public void configureServlets() {
		
		bind(GuiceContainer.class);
		
		final ResourceConfig resources = new PackagesResourceConfig("com.matera.hellomicroservices.rest");
		for (Class<?> resource : resources.getClasses()) {			
			bind(resource);
		}
		serve("/*").with(GuiceContainer.class);
		
	}
	
	@Provides
	@Singleton
	public ObjectMapper getObjectMapper() {
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper;
	}

}

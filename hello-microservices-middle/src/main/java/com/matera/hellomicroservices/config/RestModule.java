package com.matera.hellomicroservices.config;

import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;

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
	

}

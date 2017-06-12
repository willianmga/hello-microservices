package com.matera.microservices.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import matera.com.hellomicroservices.core.config.HelloMicroservicesJacksonModule;

public class Bootstrap extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		
		return Guice.createInjector(new RestModule(), 
									new HelloMicroservicesJacksonModule(), 
									new HelloMicroservicesClientModule());
		
	}

}

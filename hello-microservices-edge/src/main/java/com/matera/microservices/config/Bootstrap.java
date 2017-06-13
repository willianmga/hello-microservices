package com.matera.microservices.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import matera.com.hellomicroservices.core.config.HelloMicroservicesHttpClientModule;
import matera.com.hellomicroservices.core.config.HelloMicroservicesJacksonModule;

public class Bootstrap extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		
		return Guice.createInjector(new ServiceModule(), 
									new RestModule(),
									new HelloMicroservicesHttpClientModule(),
									new HelloMicroservicesJacksonModule(), 
									new HelloMicroservicesClientModule());
		
	}

}

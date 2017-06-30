package com.matera.microservices.config;

import com.google.inject.Binder;
import com.netflix.karyon.server.ServerBootstrap;

import matera.com.hellomicroservices.core.config.HelloMicroservicesHttpClientModule;
import matera.com.hellomicroservices.core.config.HelloMicroservicesJacksonModule;

public class Bootstrap extends ServerBootstrap {

	@Override
	protected void configureBinder(Binder binder) {
		
		binder.install(new ServiceModule());
		binder.install(new HelloMicroservicesHttpClientModule());
		binder.install(new HelloMicroservicesJacksonModule());
		binder.install(new HelloMicroservicesClientModule());
		
	}

}

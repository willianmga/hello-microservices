package com.matera.hellomicroservices.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class Bootstrap extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		
		return Guice.createInjector(new StoreModule(), new RestModule());
		
	}

}

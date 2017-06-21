package com.matera.hellomicroservices.config;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class Bootstrap extends GuiceServletContextListener {

	private static final String DB_STRATEGY = "db.strategy";
	private static final String IN_MEMORY_STRATEGY = "in_memory";
	
	@Override
	protected Injector getInjector() {
	
		List<AbstractModule> modules = new ArrayList<>();
		modules.add(new RestModule());
		modules.add(new HelloMicroservicesMiddleModule());
		
		final String dbStrategy = System.getProperty(DB_STRATEGY);
		if (IN_MEMORY_STRATEGY.equalsIgnoreCase(dbStrategy)) {
			modules.add(new InMemoryStoreModule());
		} else {
			modules.add(new MySQLStoreModule());
		}
		
		return Guice.createInjector(modules);
		
	}

}

package com.matera.hellomicroservices.config;

import com.google.inject.Binder;
import com.netflix.karyon.server.ServerBootstrap;

public class Bootstrap extends ServerBootstrap {

	private static final String DB_STRATEGY = "db.strategy";
	private static final String IN_MEMORY_STRATEGY = "in_memory";
	
	@Override
	protected void configureBinder(Binder binder) {
		
		binder.install(new RestModule());
		binder.install(new HelloMicroservicesMiddleModule());
		
		final String dbStrategy = System.getProperty(DB_STRATEGY);
		if (IN_MEMORY_STRATEGY.equalsIgnoreCase(dbStrategy)) {
			binder.install(new InMemoryStoreModule());
		} else {
			binder.install(new MySQLStoreModule());
		}
		
	}

}

package com.matera.hellomicroservices.config;

import javax.persistence.EntityManager;

import com.google.inject.AbstractModule;
import com.matera.hellomicroservices.store.PersonStore;
import com.matera.hellomicroservices.store.PersonStoreMySQL;

public class MySQLStoreModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(EntityManager.class).toProvider(HelloMicroservicesEntityManagerProvider.class);
		bind(PersonStore.class).to(PersonStoreMySQL.class);
		
	}

}

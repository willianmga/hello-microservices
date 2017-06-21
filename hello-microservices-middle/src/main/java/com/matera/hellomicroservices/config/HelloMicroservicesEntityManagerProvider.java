package com.matera.hellomicroservices.config;

import javax.persistence.EntityManager;

import com.google.inject.Provider;
import com.matera.hellomicroservices.store.MySQLConnectionFactory;

public final class HelloMicroservicesEntityManagerProvider implements Provider<EntityManager> {

	@Override
	public EntityManager get() {

		return MySQLConnectionFactory.getEntityManager();
		
	}

}

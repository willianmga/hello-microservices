package com.matera.hellomicroservices.config;

import com.google.inject.AbstractModule;
import com.matera.hellomicroservices.store.PersonStore;
import com.matera.hellomicroservices.store.PersonStoreHashMap;

public class InMemoryStoreModule extends AbstractModule {

	@Override
	protected void configure() {
		
		bind(PersonStore.class).to(PersonStoreHashMap.class);
		
	}

}

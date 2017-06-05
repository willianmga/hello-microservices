package com.matera.hellomicroservices.config;

import com.google.inject.AbstractModule;
import com.matera.hellomicroservices.store.PersonStore;
import com.matera.hellomicroservices.store.PersonStoreJPA;

public class StoreModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(PersonStore.class).to(PersonStoreJPA.class);

	}

}

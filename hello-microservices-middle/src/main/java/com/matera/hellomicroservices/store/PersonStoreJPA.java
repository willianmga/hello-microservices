package com.matera.hellomicroservices.store;

import matera.com.hellomicroservices.core.domain.Person;

public class PersonStoreJPA implements PersonStore {

	public void insert(Person person) {
		
	}

	public Long getLastID() {
		return 0L;
	}

	public Person getById(Long id) {
		return null;
	}

}

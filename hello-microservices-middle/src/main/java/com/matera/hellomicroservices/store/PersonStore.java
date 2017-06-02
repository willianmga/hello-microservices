package com.matera.hellomicroservices.store;

import matera.com.hellomicroservices.core.domain.Person;

public interface PersonStore {
	
	public Long getLastID();
	
	public Person getById(Long id);
	
	void insert(Person person);

}

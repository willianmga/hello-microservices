package com.matera.hellomicroservices.store;

import java.util.UUID;

import com.matera.hellomicroservices.entities.Person;

public interface PersonStore {
	
	void insert(Person person);
	
	public Person getById(UUID id);

}

package com.matera.hellomicroservices.store;

import java.util.UUID;

import com.matera.hellomicroservices.entities.Person;

public interface PersonStore {
	
	public Person getById(UUID id);
	
	Person insert(Person person);

}

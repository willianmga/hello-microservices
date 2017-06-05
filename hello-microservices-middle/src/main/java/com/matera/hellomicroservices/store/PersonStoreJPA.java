package com.matera.hellomicroservices.store;

import java.util.HashMap;
import java.util.UUID;

import com.matera.hellomicroservices.entities.Person;

public class PersonStoreJPA implements PersonStore {
	
	static HashMap<UUID, Person> fakeData = new HashMap<UUID, Person>();

	public Person insert(Person person) {
		
		person.setId(UUID.randomUUID());
		
		fakeData.put(person.getId(), person);
		
		return person;
		
	}

	public Person getById(UUID id) {
		
		return fakeData.getOrDefault(id, null);
		
	}

}

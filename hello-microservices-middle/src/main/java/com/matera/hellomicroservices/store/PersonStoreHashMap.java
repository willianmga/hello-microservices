package com.matera.hellomicroservices.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.matera.hellomicroservices.entities.Person;

public class PersonStoreHashMap implements PersonStore {
	
	static HashMap<UUID, Person> fakeData = new HashMap<UUID, Person>();

	public void insert(Person person) {
		
		fakeData.put(person.getId(), person);
		
	}

	public Person getById(UUID id) {
		
		return fakeData.getOrDefault(id, null);
		
	}

	public List<Person> findAllPeople() {
		
		List<Person> people = new ArrayList<Person>();
		
		for (UUID id : fakeData.keySet()) {
			people.add(fakeData.get(id));
		}
		
		return people;

	}

}

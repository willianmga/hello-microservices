package com.matera.hellomicroservices.store;

import java.util.List;
import java.util.UUID;

import com.matera.hellomicroservices.entities.Person;

public interface PersonStore {
	
	void insert(Person person);
	
	public Person getById(UUID id);

	List<Person> findAllPeople();

	List<Person> findByZipCode(String zipCode);

	List<Person> findByFirstAndOrLastName(String firstName, String lastName);

}

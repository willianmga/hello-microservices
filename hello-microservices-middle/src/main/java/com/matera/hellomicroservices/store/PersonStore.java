package com.matera.hellomicroservices.store;

import java.util.List;
import java.util.UUID;

import com.matera.hellomicroservices.entities.Person;

public interface PersonStore {
	
	void insert(Person person);
	void update(Person person);
	void delete(UUID id);
	
	Person getById(UUID id);
	List<Person> findAllPeople();

}

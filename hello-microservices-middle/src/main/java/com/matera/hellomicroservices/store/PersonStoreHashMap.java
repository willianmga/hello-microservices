package com.matera.hellomicroservices.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.matera.hellomicroservices.entities.Person;

public class PersonStoreHashMap implements PersonStore {
	
	private static HashMap<UUID, Person> fakeData = new HashMap<UUID, Person>();

	public void insert(Person person) {
		fakeData.put(person.getId(), person);
	}

	public Person getById(UUID id) {
		return fakeData.getOrDefault(id, null);
	}

	public List<Person> findAllPeople() {
		
		return new ArrayList<Person>(fakeData.values());
		
	}

	public List<Person> findByZipCode(String zipCode) {
		
		List<Person> found = new ArrayList<Person>();
		
		List<Person> people = this.findAllPeople();
		
		for (Person person : people) {
			
			if (person.getAddress().getZipCode().equalsIgnoreCase(zipCode))
				found.add(person);
			
		}

		return found;
		
	}

	public List<Person> findByFirstAndOrLastName(String firstName, String lastName) {
		
		List<Person> found = new ArrayList<Person>();
		
		List<Person> people = this.findAllPeople();
		
		boolean validateBoth = (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName)); 
		
		for (Person person : people) {
			
			if (validateBoth) {
				
				if ((person.getFirstName().equals(firstName)) && person.getLastName().equals(lastName))
					found.add(person);
				
			} else {
				
				if ((person.getFirstName().equals(firstName)) || person.getLastName().equals(lastName))
					found.add(person);
				
			}
			
		}

		return found;
		
	}

}

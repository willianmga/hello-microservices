package com.matera.hellomicroservices.service;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.matera.hellomicroservices.store.PersonStore;
import com.matera.hellomicroservices.store.PersonStoreJPA;

import matera.com.hellomicroservices.core.converter.PersonConverter;
import matera.com.hellomicroservices.core.entities.Person;

public class PersonService {
	
	private PersonStore store;

	@Inject
	public PersonService(PersonStoreJPA store) {
		this.store = store;
	}
	
	public Person insert(Person person) {
		
		checkArgument(StringUtils.isNotBlank(person.getFirstName()), "the first name must not be null");
		checkArgument(StringUtils.isNotBlank(person.getLastName()), "the last name must not be null");
		checkArgument(StringUtils.isNotBlank(person.getEmail()), "the email must not be null");
		checkArgument(StringUtils.isNotBlank(person.getNickName()), "the nickname must not be null");
		checkArgument(StringUtils.isNotBlank(person.getAdress().getCity()), "the city must not be null");
		checkArgument(StringUtils.isNotBlank(person.getAdress().getState()), "the state must not be null");
		checkArgument(StringUtils.isNotBlank(person.getAdress().getCountry()), "the country must not be null");
		checkArgument(StringUtils.isNotBlank(person.getAdress().getZipcode()), "the zipcode must not be null");
		
		person.setId(store.getLastID() +1);
		store.insert(PersonConverter.toDomain(person));
		
		return this.findById(person.getId());
		
	}

	private Person findById(Long id) {
		return PersonConverter.toEntity(store.getById(id));
	}

}


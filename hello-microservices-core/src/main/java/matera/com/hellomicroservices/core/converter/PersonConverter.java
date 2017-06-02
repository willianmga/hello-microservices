package matera.com.hellomicroservices.core.converter;

import matera.com.hellomicroservices.core.domain.Person;

public class PersonConverter {
	
	private PersonConverter() {
		
	}
	
	public static Person toDomain(matera.com.hellomicroservices.core.entities.Person person) {
		
		Person domain = new Person(person.getFirstName(), 
								   person.getLastName(), 
								   person.getEmail(), 
								   person.getNickName(),
								   AddressConverter.toDomain(person.getAdress()));		

		return domain;
		
	}
	
	public static matera.com.hellomicroservices.core.entities.Person toEntity(Person person) {
		
		matera.com.hellomicroservices.core.entities.Person entity = new matera.com.hellomicroservices.core.entities.Person();
		entity.setId(person.getId());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setNickName(person.getNickName());
		entity.setAdress(AddressConverter.toEntity(person.getAdress()));
		
		return entity;
		
	}
	

}


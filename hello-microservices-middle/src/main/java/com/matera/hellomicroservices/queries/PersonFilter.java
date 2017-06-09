package com.matera.hellomicroservices.queries;

import java.util.Objects;
import java.util.function.Predicate;

import com.matera.hellomicroservices.entities.Person;

public class PersonFilter implements Predicate<Person> {

	private Predicate<Person> firstName;
	private Predicate<Person> lastName;
	private Predicate<Person> zipcode;
	
	private PersonFilter(Predicate<Person> firstName, Predicate<Person> lastName, Predicate<Person> zipcode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.zipcode = zipcode;
	}
	
	@Override
	public boolean test(Person person) {
		return zipcode.and(firstName).and(lastName).test(person);
	}

	public static class Builder {
	
		private String firstName;
		private String lastName;
		private String zipcode;
		
		public Builder withFirstName(String value) {
			firstName = value;
			return this;
		}
		
		public Builder withLastName(String value) {
			lastName = value;
			return this;
		}
		
		public Builder withZipCode(String value) {
			zipcode = value;
			return this;
		}
		
		public PersonFilter build() {
			
			Predicate<Person> byFirstName = (person) -> (firstName != null) ? Objects.equals(firstName, person.getFirstName()) : true; 
			
			Predicate<Person> byLastName = (person) -> (lastName != null) ? Objects.equals(lastName, person.getLastName()) : true;
			
			Predicate<Person> byZipCode = (person) -> (zipcode != null) ? Objects.equals(zipcode, person.getAddress().getZipCode()) : true;
			
			return new PersonFilter(byFirstName, byLastName, byZipCode);
		}
		
	}
	
}

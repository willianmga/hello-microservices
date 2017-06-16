package com.matera.hellomicroservices.queries;

public class PersonQuery {

	private String firstName;
	private String lastName;
	private String zipCode;

	private PersonQuery(Builder builder) {
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.zipCode = builder.zipCode;
	}
	
	public static class Builder {
		
		public String zipCode;
		public String lastName;
		public String firstName;
		
		public PersonQuery build() {
			
			return new PersonQuery(this);
			
		}

		public Builder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public Builder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public Builder withZipCode(String zipCode) {
			this.zipCode = zipCode;
			return this;
		}
		
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getZipCode() {
		return zipCode;
	}
	
}

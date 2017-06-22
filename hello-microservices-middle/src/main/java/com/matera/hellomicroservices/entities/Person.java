package com.matera.hellomicroservices.entities;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.apache.commons.lang.StringUtils;

@Entity
public class Person {
	
	@Id
	private UUID id;
	private String firstName;
	private String lastName;
	private String email;
	private String nickName;
	
	@JoinColumn
	@OneToOne(cascade = CascadeType.PERSIST)
	private Address address;
	
	private Person() {
		super();
	}
	
	private Person(Builder builder) {
		
	    this.id = builder.id;
	    this.firstName = builder.firstName;
	    this.lastName = builder.lastName;
		this.email = builder.email;
		this.nickName = builder.nickName;
		this.address = builder.address;
		
	}
	
	public static class Builder {
		
		private UUID id;
		private String firstName;
		private String lastName;
		private String email;
		private String nickName;
		private Address address;
		
		public Person build() {
			
			checkArgument(id != null, "the id must not be null");
			checkArgument(StringUtils.isNotBlank(firstName), "the first name must not be null");
			checkArgument(StringUtils.isNotBlank(lastName), "the last name must not be null");
			checkArgument(StringUtils.isNotBlank(email), "the email must not be null");
			checkArgument(StringUtils.isNotBlank(nickName), "the nickname must not be null");
			
			return new Person(this);
		}
		
		public Builder withUUID(UUID id) {
			this.id = id;
			return this;
		}
		
		public Builder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public Builder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}			
		
		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}
		
		public Builder withNickName(String nickName) {
			this.nickName = nickName;
			return this;
		}	
		
		public Builder withAddress(Address address) {
			this.address = address;
			return this;
		}					
		
	}
	
	public UUID getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public Address getAddress() {
		return address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		return true;
	}

}

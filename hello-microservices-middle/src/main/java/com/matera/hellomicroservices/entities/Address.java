package com.matera.hellomicroservices.entities;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;

@Entity
public class Address {
	
	@Id
	private UUID id;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	
	private Address() {
		super();
	}
	
	private Address(Builder builder) {
		
		this.id = UUID.randomUUID();
		this.city = builder.city;
		this.state = builder.state;
		this.country = builder.country;
		this.zipCode = builder.zipCode;
		
	}	
	
	public static class Builder {
		
		private String city;
		private String state;
		private String country;
		private String zipCode;
		
		public Address build() {
			
			checkArgument(StringUtils.isNotBlank(city), "the city must not be null");
			checkArgument(StringUtils.isNotBlank(state), "the state must not be null");
			checkArgument(StringUtils.isNotBlank(country), "the country must not be null");
			checkArgument(StringUtils.isNotBlank(zipCode), "the zipCode must not be null");
			
			return new Address(this);
		}		
		
		public Builder withCity(String city) {
			this.city = city;
			return this;
		}
		
		public Builder withState(String state) {
			this.state = state;
			return this;
		}			
		
		public Builder withCountry(String country) {
			this.country = country;
			return this;
		}		
		
		public Builder withZipCode(String zipCode) {
			this.zipCode = zipCode;
			return this;
		}			
		
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getZipCode() {
		return zipCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
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
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}
	
}

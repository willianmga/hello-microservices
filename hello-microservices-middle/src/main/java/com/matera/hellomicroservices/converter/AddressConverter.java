package com.matera.hellomicroservices.converter;

import com.matera.hellomicroservices.entities.Address;

import matera.com.hellomicroservices.core.responses.AddressResource;

public class AddressConverter {
	
	private AddressConverter() {
		
	}

	public static AddressResource convertToAddressResource(Address address) {
		
		AddressResource resource = new AddressResource.Builder()
				.withCity(address.getCity())
				.withState(address.getState())
				.withCountry(address.getCountry())
				.withZipCode(address.getZipCode())
				.build();

		return resource;
		
	}

}

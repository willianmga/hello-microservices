package matera.com.hellomicroservices.core.converter;

import matera.com.hellomicroservices.core.domain.Address;

public class AddressConverter {
	
	private AddressConverter() {
		
	}

	public static Address toDomain(matera.com.hellomicroservices.core.entities.Address address) {
	
		Address domain = new Address(address.getCity(),
									 address.getState(),
									 address.getCountry(),
									 address.getZipcode());
		
		return domain;
		
	}

	public static matera.com.hellomicroservices.core.entities.Address toEntity(Address address) {
		
		matera.com.hellomicroservices.core.entities.Address entity = new matera.com.hellomicroservices.core.entities.Address();
		entity.setCity(address.getCity());
		entity.setState(address.getState());
		entity.setCountry(address.getCountry());
		entity.setZipcode(address.getZipcode());
		
		return entity;
		
	}

}

package com.matera.microservices.api;

import java.util.UUID;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PersonResource;
import rx.Observable;

/**
 * 
 * @author willianmga
 *
 */

public interface PersonClient {

	/**
	 * Creates a person
	 * 
	 * @param the request object to create person
	 * @return Observable<CreatePersonResponse>
	 * 
	 */
	public Observable<CreatePersonResponse> createPerson(CreatePersonRequest request);
	
	
	/**
	 * Finds a person by it's id on middle service 
	 * 
	 * @param the person uuid
	 * @return {@link Observable<PersonResource}
	 * 
	 */
	public Observable<PersonResource> findPersonByUUID(UUID id);
	

}

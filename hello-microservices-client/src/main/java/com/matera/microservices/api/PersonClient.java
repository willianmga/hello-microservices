package com.matera.microservices.api;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
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
	

}

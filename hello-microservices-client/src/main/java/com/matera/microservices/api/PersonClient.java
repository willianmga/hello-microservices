package com.matera.microservices.api;

import java.util.UUID;

import com.matera.hellomicroservices.queries.PersonQuery;

import matera.com.hellomicroservices.core.requests.CreatePersonRequest;
import matera.com.hellomicroservices.core.responses.CreatePersonResponse;
import matera.com.hellomicroservices.core.responses.PeopleResource;
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
	public Observable<CreatePersonResponse> create(final CreatePersonRequest request);
	
	/**
	 * Updates a person 
	 * 
	 * @param id, request
	 * @return
	 */
	public Observable<CreatePersonResponse> update(final UUID id, final CreatePersonRequest request);
	
	/**
	 * Deletes a person
	 * 
	 * @param id
	 * @return
	 */
	public Observable<Integer> delete(final UUID id);
	
	/**
	 * Finds a person by it's id on middle service 
	 * 
	 * @param the person uuid
	 * @return {@link Observable<PersonResource}
	 * 
	 */
	public Observable<PersonResource> searchByUUID(final UUID id);
	
	
	/**
	 * Find people by their attributes
	 * 
	 * @param personQuery
	 * @return {@link Observable<PersonResource}
	 */
	public Observable<PeopleResource> searchBy(final PersonQuery personQuery);
	
}

package com.matera.hellomicroservices.store;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.matera.hellomicroservices.entities.Person;

public final class PersonStoreMySQL implements PersonStore {
	
	private final EntityManager entityManager;

	@Inject
	public PersonStoreMySQL(EntityManager entityManager) {
		
		this.entityManager = entityManager;
		
	}

	@Override
	public void insert(Person person) {

		try {
			
			System.out.println("vou começar a persistir a pessoa");			
			
			if (!entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().begin();
			}
			
			entityManager.persist(person);
			entityManager.flush();
			entityManager.getTransaction().commit();
			
			System.out.println("terminei de persistir a pessoa");
		
		} catch(Exception e) {
			
			System.out.println("Deu ruim...");
			
			e.printStackTrace();
				
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			
		}
		
	}

	@Override
	public void update(Person person) {

		try {
			
			if (!entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().begin();
			}
			
			entityManager.merge(person);
			entityManager.flush();
			entityManager.getTransaction().commit();
		
		} catch(Exception e) {
			
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			
		}		

	}

	@Override
	public void delete(UUID id) {
		
		try {
			
			if (!entityManager.getTransaction().isActive())
				entityManager.getTransaction().begin();
			Person deleted = entityManager.find(Person.class, id);
			entityManager.remove(deleted);
			entityManager.getTransaction().commit();
		
		} catch(Exception e) {
			
			if (entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			
		}			

	}

	@Override
	public Person getById(UUID id) {
		
		return entityManager.find(Person.class, id);
		
	}
	
	@Override
	@SuppressWarnings("unchecked")	
	public List<Person> findAllPeople() {
		
		return (List<Person>) entityManager.createQuery("from Person").getResultList();
		
	}

}

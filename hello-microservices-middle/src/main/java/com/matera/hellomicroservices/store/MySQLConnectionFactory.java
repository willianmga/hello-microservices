package com.matera.hellomicroservices.store;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class MySQLConnectionFactory {
	
	private static EntityManagerFactory INSTANCE;
	
	public static EntityManager getEntityManager() {
		
		System.out.println("iniciando get entitymanager");
		
		if (INSTANCE == null) {
			INSTANCE = Persistence.createEntityManagerFactory("hibernateMySQLPU");
		}
		
		System.out.println("terminando get entitymanager");
		
		return INSTANCE.createEntityManager();
		
	}

}

package jpa;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jpa.Entites.*;

public class JpaTest {

	public static EntityManager manager;
	public static EntityTransaction tx;
	public static boolean alreadyexist (String username) {
		String querystring = "SELECT u FROM User u WHERE u.username = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", username);
		return !query.getResultList().isEmpty();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		manager = EntityManagerHelper.getEntityManager();
		tx = manager.getTransaction();
		tx.begin();


		try {
			User firstUser = new User("test");
			manager.persist(firstUser);
		
			User firstUser2 = manager.find(User.class, (long)1);
			//System.out.println(firstUser.getUsername());
			//Events firstEvent = new Events();
			//firstEvent.setCreator(firstUser);
			//manager.persist(firstEvent);
			
			System.out.println(alreadyexist("test"));
			System.out.println(alreadyexist("test2"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();


		manager.close();
		EntityManagerHelper.closeEntityManagerFactory();
		//		factory.close();
	}


}

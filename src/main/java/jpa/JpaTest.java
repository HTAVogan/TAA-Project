package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import jpa.Entites.*;

public class JpaTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		EntityManager manager = EntityManagerHelper.getEntityManager();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();


		try {
			/*User firstUser = new User("test");
			manager.persist(firstUser);*/
		
			User firstUser = manager.find(User.class, (long)1);
			//System.out.println(firstUser.getUsername());
			Events firstEvent = new Events();
			firstEvent.setCreator(firstUser);
			manager.persist(firstEvent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();


		manager.close();
		EntityManagerHelper.closeEntityManagerFactory();
		//		factory.close();
	}


}

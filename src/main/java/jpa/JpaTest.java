package jpa;


import java.sql.Date;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.mapping.Array;
import org.hibernate.mapping.List;

import jpa.Entites.*;

public class JpaTest {
	/**name
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManager manager = EntityManagerHelper.getEntityManager();
		DAO dao = new DAO (manager,manager.getTransaction());
		try {
			dao.CreateUser("test25", "blabla");
			dao.CreateUser("5555555", "bibi");
			User r = dao.getUserByUsername("Test26");
			StyleMusic sm = dao.addstylemusic("rnb");
			dao.addtofav(r, sm);
			String newEventUrl = "https://www.facebook.com/search/top/?q=we%20are%20rave%20-%20jacidorex%2C%20subway%20shamans%2C%20acid%20division%2C%20nz42";
			Date start = new Date(118,  8,  21);
			Date end = new Date(119, 8, 22);//Date) new java.util.Date(118, 8, 21, 23, 0);
			dao.createEvent("We are Rave", dao.CreateLocation("Nantes", 2), r, newEventUrl, start , end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dao.tx.commit();
		manager.close();
		EntityManagerHelper.closeEntityManagerFactory();
		//		factory.close();
	}


	



	}

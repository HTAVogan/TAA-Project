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
	public static DAOUser daou;
	public static DAOLocation daolo;
	public static DAOMusic daomu;
	public static DAOEvents daoe;
	
	public static void main(String[] args) {
		EntityManager manager = EntityManagerHelper.getEntityManager();
		initDAO(manager, manager.getTransaction());
		try {
			daou.userAlreadyexist("test25");
			daou.CreateUser("test", "blabla");
			daou.CreateUser("55555", "bibi");
			User r = daou.getUserByUsername("Test26");
			StyleMusic sm = daomu.addstylemusic("rnb");
			daou.addtofav(r, sm);
			String newEventUrl = "https://www.facebook.com/search/top/?q=we%20are%20rave%20-%20jacidorex%2C%20subway%20shamans%2C%20acid%20division%2C%20nz42";
			Date start = new Date(118,  8,  21);
			Date end = new Date(119, 8, 22);//Date) new java.util.Date(118, 8, 21, 23, 0);
			Location location = daolo.CreateLocation("Nantes", 2);
			System.out.println(location.getName());
			Events WeAreRave = daoe.createEvent("We are Rave", location, r, newEventUrl, start , end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		manager.close();
		EntityManagerHelper.closeEntityManagerFactory();
		//		factory.close();
	}
	public static void initDAO(EntityManager manager,EntityTransaction tx) {
		daou = new DAOUser();
		daomu = new DAOMusic();
		daolo = new DAOLocation();
		daoe = new DAOEvents();
	}
}

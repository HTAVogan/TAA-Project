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

	public static EntityManager manager;
	public static EntityTransaction tx;
	
	public static boolean userAlreadyexist (String username) {
		String querystring = "SELECT u FROM User u WHERE u.username = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", username);
		return !query.getResultList().isEmpty();
	}
	
	public static User CreateUser (String username, String password) {
		if(userAlreadyexist(username)) {
			// Throw user already exist error
			System.out.println("Erreur name already exist");
			return null;
		}else {
			User newUser = new User(username, password);
			manager.persist(newUser);
			return newUser;
		}
	}
	
	public static User getUserByUsername(String username) {
		String querystring = "SELECT u FROM User u WHERE u.username = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", username);
		if(query.getResultList().isEmpty()) {
			//throw missing user error
			return null;
		}else {
			return (User) query.getResultList().get(0);
		}
	}
	
	public static User getUserById(long id) {
		User foundUser = manager.find(User.class, id);
		return foundUser;
	}
	
	public static Events[] lookForEventsByName(String searchString){
		String querystring = "SELECT e FROM Events e WHERE e.title = :search";
		Query query = manager.createQuery(querystring);
		query.setParameter("search", searchString);
		Events[] foundEvents = new Events[query.getResultList().size()];
		for(int i = 0; i < query.getResultList().size(); i++) {
			foundEvents[i] = (Events)query.getResultList().get(i);
		}
		return foundEvents;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		manager = EntityManagerHelper.getEntityManager();
		tx = manager.getTransaction();
		tx.begin();


		try {
			CreateUser("test25", "blabla");
			CreateUser("Test26", "bibi");
			User r = getUserByUsername("Test26");
			StyleMusic sm =addstylemusic("rnb");
			addtofav(r, sm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();


		manager.close();
		EntityManagerHelper.closeEntityManagerFactory();
		//		factory.close();
	}
	public static StyleMusic addstylemusic(String style) {
		if(musicalreadyexist(style)) {
			System.out.println("This style already exist");
			return null;
		}
		else {
			StyleMusic stylem = new StyleMusic (style);
			manager.persist(stylem);
			return stylem;
		}
	
	}
	public static boolean musicalreadyexist(String style) {
		String querystring = "SELECT m FROM StyleMusic m WHERE m.style = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", style);
		return !query.getResultList().isEmpty();
	}
	public static void addtofav (User u,StyleMusic sm ) {
		u.getFavoriteStyles().add(sm);
	}
	public static void createEvent(String name,Location l,User u,Date start, Date end) {
		Events e = new Events(name,u,start,end,l);
		if(eventAlreadyExist(name)) {
			System.out.println("Error name already exist for this event");		}
		else {
			manager.persist(e);	
		}
	}
	public static boolean eventAlreadyExist(String s) {
		String querystring = "SELECT e FROM Events e WHERE e.title = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", s);
		return !query.getResultList().isEmpty();
	}
}

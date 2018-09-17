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
			System.out.println("Error,'"+username+"' already exist");
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
			System.err.println("No User with username '" + username + "' has been found!");
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
	
	/**name
	 * @param args
	 */
	public static void main(String[] args) {

		manager = EntityManagerHelper.getEntityManager();
		tx = manager.getTransaction();
		tx.begin();

		try {
			CreateUser("test25", "blabla");
			CreateUser("5555555", "bibi");
			User r = getUserByUsername("Test26");
			StyleMusic sm = addstylemusic("rnb");
			addtofav(r, sm);
			String newEventUrl = "https://www.facebook.com/search/top/?q=we%20are%20rave%20-%20jacidorex%2C%20subway%20shamans%2C%20acid%20division%2C%20nz42";
			Date start = new Date(118,  8,  21);
			Date end = new Date(119, 8, 22);//Date) new java.util.Date(118, 8, 21, 23, 0);
			createEvent("We are Rave", CreateLocation("Nantes", 2), r, newEventUrl, start , end);
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
			System.out.println("The music style '" + style + "' already exist");
			return null;
		}
		else {
			StyleMusic stylem = new StyleMusic (style);
			manager.persist(stylem);
			return stylem;
		}
	
	}
	public static boolean locationAlreadyExist(String name) {
		String querystring = "SELECT l FROM Location WHERE l.name = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", name);
		return !query.getResultList().isEmpty();
	}
	
	public static Location CreateLocation(String name, int type) {
		if(locationAlreadyExist(name)) {
			// throw Location already exist error
			System.err.println("A location named '" + name + "' already exists");
			return null;
		}else {
			Location ret = new Location();
			switch(type) {
			case 0:
				ret = new Region(name);
				break;
			case 1:
				ret = new Departement(name);
				break;
			case 2:
				ret = new Ville(name);
				break;
			}
			manager.persist(ret);
			return ret;
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
	public static void createEvent(String name,Location l,User u, String url, Date start, Date end) {
		if(eventAlreadyExist(name)) {
			System.out.println("Error title '" + name + "' already exist for this event");		}
		else {
			Events e = new Events(name,u,start,end,l, url);
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

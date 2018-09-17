package jpa;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import jpa.Entites.Departement;
import jpa.Entites.Events;
import jpa.Entites.Location;
import jpa.Entites.Region;
import jpa.Entites.StyleMusic;
import jpa.Entites.User;
import jpa.Entites.Ville;

public class DAO {
	public  EntityManager manager;
	public  EntityTransaction tx;
	public DAO (EntityManager manager,EntityTransaction tx) {
		this.manager=manager;
		this.tx=tx;
		this.tx.begin();
	}
	public boolean userAlreadyexist (String username) {
		String querystring = "SELECT u FROM User u WHERE u.username = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", username);
		return !query.getResultList().isEmpty();
	}
	
	public User CreateUser (String username, String password) {
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
	
	public  User getUserByUsername(String username) {
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
	
	public  User getUserById(long id) {
		User foundUser = manager.find(User.class, id);
		return foundUser;
	}
	public  StyleMusic addstylemusic(String style) {
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
	public  boolean musicalreadyexist(String style) {
		String querystring = "SELECT m FROM StyleMusic m WHERE m.style = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", style);
		return !query.getResultList().isEmpty();
	}
	public  void addtofav (User u,StyleMusic sm ) {
		u.getFavoriteStyles().add(sm);
	}
	public  void createEvent(String name,Location l,User u, String url, Date start, Date end) {
		Events e = new Events(name,u,start,end,l,url);
		if(eventAlreadyExist(name)) {
			System.out.println("Error title '" + name + "' already exist for this event");		}
		else {
			manager.persist(e);	
		}
	}
	public  boolean eventAlreadyExist(String s) {
		String querystring = "SELECT e FROM Events e WHERE e.title = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", s);
		return !query.getResultList().isEmpty();
	}

	public  Events[] lookForEventsByName(String searchString){
		String querystring = "SELECT e FROM Events e WHERE e.title = :search";
		Query query = manager.createQuery(querystring);
		query.setParameter("search", searchString);
		Events[] foundEvents = new Events[query.getResultList().size()];
		for(int i = 0; i < query.getResultList().size(); i++) {
			foundEvents[i] = (Events)query.getResultList().get(i);
		}
		return foundEvents;
	}
	public  boolean locationAlreadyExist(String name) {
		String querystring = "SELECT l FROM Location WHERE l.name = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", name);
		return !query.getResultList().isEmpty();
	}
	
	public  Location CreateLocation(String name, int type) {
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
	
}

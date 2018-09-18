package jpa;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import jpa.Entites.Events;
import jpa.Entites.Location;
import jpa.Entites.User;

public class DAOEvents {
	public EntityManager manager;
	public EntityTransaction tx;

	public DAOEvents() {
		this.manager = EntityManagerHelper.getEntityManager();
	}

	public Events[] getEventsByUser(User user) {
		String querystring = "SELECT e FROM Events e WHERE e.CREATOR_USER_ID = :id";
		Query query = manager.createQuery(querystring);
		query.setParameter("id", user.getUser_id());
		Events[] foundEvents = new Events[query.getResultList().size()];
		for (int i = 0; i < query.getResultList().size(); i++) {
			foundEvents[i] = (Events) query.getResultList().get(i);
		}
		return foundEvents;
	}

	public Events[] getEventsByID(long id) {
		String querystring = "SELECT e FROM Events e WHERE e.CREATOR_USER_ID = :id";
		Query query = manager.createQuery(querystring);
		query.setParameter("id", id);
		Events[] foundEvents = new Events[query.getResultList().size()];
		for (int i = 0; i < query.getResultList().size(); i++) {
			foundEvents[i] = (Events) query.getResultList().get(i);
		}
		return foundEvents;
	}

	public Events[] getEventsByUsername(String username) {
		String querystring = "SELECT e FROM Events e WHERE e.CREATOR_USER_ID = :id";
		Query query = manager.createQuery(querystring);
		DAOUser daou = new DAOUser();
		User user = daou.getUserByUsername(username);
		query.setParameter("id", user.getUser_id());
		Events[] foundEvents = new Events[query.getResultList().size()];
		for (int i = 0; i < query.getResultList().size(); i++) {
			foundEvents[i] = (Events) query.getResultList().get(i);
		}
		return foundEvents;
	}

	public Events createEvent(String name, Location l, User u, String url, Date start, Date end) {

		this.tx = EntityManagerHelper.getEntityManager().getTransaction();
		this.tx.begin();

		if(eventAlreadyExist(name)) {
			System.out.println("Error title '" + name + "' already exist for this event");
			return null;
		}
		else {
			System.out.println(u.getUser_id() + " creates an Event with url '" + url+ "'");
			Events e = new Events(name,u,start,end,l,url);
			manager.persist(e);	
			this.tx.commit();
			return e;
		}
	}
	public Events createEvent(String name, List<Location> ls, User u, String url, Date start, Date end) {
		this.tx = EntityManagerHelper.getEntityManager().getTransaction();
		this.tx.begin();

		if(eventAlreadyExist(name)) {
			System.out.println("Error title '" + name + "' already exist for this event");
			return null;
		}
		else {
			System.out.println(u.getUser_id() + " creates an Event with url '" + url+ "'");
			Events e = new Events(name,u,start,end,ls,url);
			manager.persist(e);	
			this.tx.commit();
			return e;
		}
	}
	public Events createEvent(String name, String url) {
		this.tx = EntityManagerHelper.getEntityManager().getTransaction();
		this.tx.begin();

		if(eventAlreadyExist(name)) {
			System.out.println("Error title '" + name + "' already exist for this event");
			return null;
		}
		else {
			System.out.println("creation of simple Event with url '" + url+ "'");
			Events e = new Events(name,url);
			manager.persist(e);	
			this.tx.commit();
			return e;
		}
	}
	
	public Events getEventById(long id) {
		String querystring = "SELECT e FROM Events e WHERE e.id = :id";
		Query query = manager.createQuery(querystring);
		query.setParameter("id", id);
		if(query.getResultList().isEmpty()) {
			//throw Event not found
			System.err.println("Error : no Events with id " + id);
			return null;
		}
		else {
			return (Events)query.getResultList().get(0);
		}
	}
	
	public boolean eventAlreadyExist(String s) {
		String querystring = "SELECT e FROM Events e WHERE e.title = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", s);
		return !query.getResultList().isEmpty();
	}
	
	public Events[] lookForEventsByName(String searchString){
		String querystring = "SELECT e FROM Events e WHERE e.title = :search";
		Query query = manager.createQuery(querystring);
		query.setParameter("search", searchString);
		Events[] foundEvents = new Events[query.getResultList().size()];
		for(int i = 0; i < query.getResultList().size(); i++) {
			foundEvents[i] = (Events)query.getResultList().get(i);
		}
		return foundEvents;
	}

}

package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import jpa.Entites.StyleMusic;
import jpa.Entites.User;

public class DAOUser {
	public  EntityManager manager;
	public  EntityTransaction tx;

	public DAOUser (EntityManager manager,EntityTransaction tx) {
		this.manager=manager;
		this.tx=tx;
		this.tx.begin();
	}
	
	public  User getUserById(long id) {
		User foundUser = manager.find(User.class, id);
		return foundUser;
	}
	public boolean userAlreadyexist(String username) {
		String querystring = "SELECT u FROM User u WHERE u.username = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", username);

		return !query.getResultList().isEmpty();
	}
	public User CreateUser(String username, String password) {
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
	public User getUserByUsername(String username) {
		String querystring = "SELECT u FROM User u WHERE u.username = :name";
		//Query query = manager.createNativeQuery(querystring, User.class);
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
	public void addtofav (User u,StyleMusic sm ) {
		u.getFavoriteStyles().add(sm);
		manager.refresh(u);
	}
}

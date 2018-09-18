package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import jpa.Entites.Departement;
import jpa.Entites.Location;
import jpa.Entites.Region;
import jpa.Entites.Ville;

public class DAOLocation {
	public  EntityManager manager;
	public  EntityTransaction tx;
	public DAOLocation () {
		manager= EntityManagerHelper.getEntityManager();
	}
	public boolean locationAlreadyExist(String name) {
		String querystring = "SELECT l FROM Location l WHERE l.name = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", name);
		return !query.getResultList().isEmpty();
	}
	public Location CreateLocation(String name, int type) {
		if(locationAlreadyExist(name)) {
			// throw Location already exist error
			System.err.println("A location named '" + name + "' already exists");
			return null;
		}else {
			tx=manager.getTransaction();
			tx.begin();
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
			tx.commit();
			return ret;
		}
	}
}

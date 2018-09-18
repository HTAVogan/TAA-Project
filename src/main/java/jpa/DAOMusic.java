package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import jpa.Entites.StyleMusic;

public class DAOMusic {
	public  EntityManager manager;
	
	public DAOMusic () {
		this.manager=EntityManagerHelper.getEntityManager();
	}
	
	public StyleMusic addstylemusic(String style) {

		EntityTransaction  tx=manager.getTransaction();
		tx.begin();
		
		if(musicalreadyexist(style)) {
			System.out.println("The music style '" + style + "' already exist");
			return null;
		}
		else {
			StyleMusic stylem = new StyleMusic (style);
			manager.persist(stylem);
			tx.commit();
			return stylem;
		}
	}
	public boolean musicalreadyexist(String style) {
		String querystring = "SELECT m FROM StyleMusic m WHERE m.style = :name";
		Query query = manager.createQuery(querystring);
		query.setParameter("name", style);
		return !query.getResultList().isEmpty();
	}
}

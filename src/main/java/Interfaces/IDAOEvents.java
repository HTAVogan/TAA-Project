package Interfaces;

import java.util.List;

import jpa.Entites.Events;

public interface IDAOEvents {
	public Events findOne(long id);
	public List<Events> findAll();
}

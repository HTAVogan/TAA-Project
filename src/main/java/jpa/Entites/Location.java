package jpa.Entites;

import javax.persistence.Id;

public class Location {
	protected long id;
	protected String name;
	
	@Id
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Location() {		
	}
	
	public Location(long id,String name) {
		this.id=id;
		this.name=name;
	}
}

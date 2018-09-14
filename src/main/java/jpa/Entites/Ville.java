package jpa.Entites;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ville {
	private long codepostale;
	@EmbeddedId
	public long getCodepostale() {
		return codepostale;
	}
	public void setCodepostale(long codepostale) {
		this.codepostale = codepostale;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String name;
	public Ville() {
		
	}
	public Ville(long id,String name) {
		this.name=name;
		codepostale=id;
	}
}

package jpa.Entites;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
	
	private long id;
	private String username;
	private String password;
	private List<StyleMusic> FavoriteStyles ;
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public User() {
		
	}
	public User(String username) {
		this.username=username;
		this.FavoriteStyles=new ArrayList<StyleMusic>();
	}
	public User(String username,String password,List<StyleMusic> lm) {
		this.username=username;
		this.FavoriteStyles=lm;
		this.password=password;
	}
	//@Column(nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@ManyToMany
	public List<StyleMusic> getFavoriteStyles() {
		return FavoriteStyles;
	}
	public void setFavoriteStyles(List<StyleMusic> favoriteStyles) {
		FavoriteStyles = favoriteStyles;
	}
}

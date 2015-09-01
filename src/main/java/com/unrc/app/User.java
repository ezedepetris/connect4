package com.unrc.app;
import java.util.Scanner;
import org.javalite.activejdbc.Model;
import java.util.List;

/*this class model an user for connect4 game*/
public class User extends Model{
	String password;/*its the password of an user*/


	static{
		validatePresenceOf("first_name");
		validatePresenceOf("last_name");
		validatePresenceOf("email");
	}

/*method constructor, create an new user*/
	public User(){
	}

	/*constructor for create a new user*/
	public User(String email, String first_name, String last_name){
		this.set("email", email);
		this.set("first_name", first_name);
		this.set("last_name", last_name);
		this.save();
	}


/*return an user*/
	public User getUser(String email){
		List<User> current = User.where("email = ?",email);
		if(!current.isEmpty() && current != null)
			return current.get(0);
		return null;
	}

	public static String setName(String n){
		return n;
	}
	/*this method return a new user*/
	public User createUser(String email, String first_name, String last_name){
		this.set("email", email);
		this.set("first_name", first_name);
		this.set("last_name", last_name);
		this.save();
		return this;
	}

	public String toStringFirstName(){
		return this.getString("first_name");
	}
	public String toStringLastName(){
		return this.getString("last_name");
	}
	public String toStringEmail(){
		return this.getString("email");
	}
	public String toId(){
		return this.getString("id");
	}


}
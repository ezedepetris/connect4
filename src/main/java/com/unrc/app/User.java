package com.unrc.app;
import org.javalite.activejdbc.Model;

/*this class model an user for connect4 game*/
public class User extends Model{
	// private String email;/*email of an user,its unique, and can be use for username*/
	// private String name;/*its the real name of an user*/
	// private String lastName;its the last name of an user
	// private Integer id;/*numer identification of an user,its the primary key,with this we find the users*/
	// private String password;/*its the password of an user*/

	static{
		validatePresenceOf("first_name");
		validatePresenceOf("last_name");
		validatePresenceOf("email");
	}

// /*method constructor, create an new user*/
// 	public User(String anEmail, String aName, String aLastName, String aPassword, Integer anId){
// 		email = anEmail;
// 		name = aName;
// 		lastName = aLastName;
// 		id = anId;
// 		password = aPassword;
// 	}

// /*return an user*/
// 	public User getUser(){
// 		return this;
// 	}

// /*set a new email for an user*/
// 	public void setEmail(String anEmail){
// 		email = anEmail;
// 	}

// /*return the current user email*/
// 	public String getEmail(){
// 		return email;
// 	}

// /*modify the name of an user*/
// 	public void setName(String aName){
// 		name = aName;
// 	}

// /*return the current user name*/
// 	public String getName(){
// 		return name;		
// 	}

// /*modify the current user last name*/
// 	public void setLastName(String aLastName){
// 		lastName = aLastName;
// 	}

// /*return the id of an user*/
// 	public Integer getId(){
// 		return id;
// 	}

// /*return the current user password*/
// 	public String getPassword(){
// 		return password;
// 	}
}
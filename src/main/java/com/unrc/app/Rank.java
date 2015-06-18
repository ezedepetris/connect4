package com.unrc.app;
import org.javalite.activejdbc.Model;

public class Rank extends Model{
	
	public Rank(){
	}

	public String toStringId(){
		return this.getString("id");
	}
	public String toStringUserId(){
		return this.getString("user_id");
	}
	public String toStringRank(){
		return this.getString("games_won");
	}
	
}
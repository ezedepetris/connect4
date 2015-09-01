package com.unrc.app;
import java.util.*;
import org.javalite.activejdbc.Model;

public class Rank extends Model{
	
	public Rank(){
	}

  /*method that update the rank after a game ended*/
	public void upDateRank(Integer winner){
		Boolean modified = false;
		int i = 0;
		int user = 0;
		List<Rank> ranking = Rank.findAll();
		
		while(i < ranking.size() && !modified){
      Rank rank = ranking.get(i);
      if(winner.compareTo((Integer)rank.get("user_id")) == 0){//Find the player on the rank list
        modified = true;
        Integer wons = (Integer)rank.get("games_won");
        rank.set("games_won", (wons+1));
        rank.save();
      }
      i++;
    }//End of while
    if(!modified){//Player wasn't on the rank list
      Rank rankNew = new Rank();
      rankNew.set("user_id", winner);
      rankNew.set("games_won", 1);
      rankNew.save();
    }
    ranking = Rank.findAll();
    i=ranking.size()-1;
    while(i > 0){
    	if((Integer)ranking.get(i).get("games_won")>(Integer)ranking.get(i-1).get("games_won")){
    		Integer gamesWonLess = (Integer)ranking.get(i).get("games_won");
    		Integer userIdLess = (Integer)ranking.get(i).get("user_id");
    		ranking.get(i).set("user_id",ranking.get(i-1).get("user_id"));
    		ranking.get(i).set("games_won",ranking.get(i-1).get("games_won"));
    		ranking.get(i).save();
    		ranking.get(i-1).set("user_id",userIdLess);
    		ranking.get(i-1).set("games_won",gamesWonLess);
    		ranking.get(i-1).save();
    	}

    	i--;
    }
	}//End of upDateRank

  


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
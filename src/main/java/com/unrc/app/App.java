package com.unrc.app;

import com.unrc.app.User;
import org.javalite.activejdbc.Base;
import java.util.Scanner;
import java.util.*;


// import static spark.Spark.*;

//     public class Appd {
//         public static void main(String[] args) {
//             get("/hello", (req, res) -> "Hello World");
//         }
//     }

    // cerrar en terminal con contro cy no control z

public class App
{
  public static void main( String[] args ){
    System.out.println( "Hello World!" );

    Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");

    // User.deleteAll();
    // Cell.deleteAll();
    // Grid.deleteAll();
    User user1 = new User();
    User user2 = new User();
    Scanner in = new Scanner(System.in);

    System.out.println("PRESS 1 TO CREATE THE USER 1\nPRESS 2 TO SEARCH THE USER 1"); 
    String number = in.next();
    /*CREATE USER 1*/
    if(number.charAt(0) == '2')
      user1 = user1.getUser();
    else
      user1 = user1.createUser();

    System.out.println("PRESS 1 TO CREATE THE USER 2\nPRESS 2 TO SEARCH THE USER 2");
    number = in.next();
    /*CREATE USER 2*/
    if(number.charAt(0) == '2')
      user2 = user2.getUser();
    else
      user2 = user2.createUser();

    /*VAR GAME CONDITION*/
    boolean playing = true;
    /*CREATE A BOARD*/
    Grid board = new Grid(6,7);
    board.save();

    int move = 0;
    int row;
    /*METHOD DATA ENTRY*/
   // Scanner in = new Scanner(System.in);
    int column;
    String columnString;
    /*CREATE A GAME*/
    Game gaming = new Game();
    board.show();
    while(playing){
      System.out.println("Player "+(move%2+1)+":");
      columnString = in.next();
      //System.out.println(columnString=='g');
      if (columnString.charAt(0)=='g' || columnString.charAt(0)=='G'){
        gaming.set("user1_id",user1.getId());
        gaming.set("user2_id",user2.getId());
        gaming.set("grid_id",board.getId());
        playing = false;
        gaming.save();
      }
      else{
        /*CREATE A CELL*/
        Cell c = new Cell();
        /*CONVERT A STRING TO INT*/
        column = Integer.parseInt(columnString);
        /*DOUBLET = (FILA,RESULT)*/
        Doublet doublet = board.play(move%2+1,column);
        c.set("pos_x", doublet.getFirst());
        c.set("pos_y", column);
        if(move%2 == 0)
          /*ASSIGN A USER TO CELL1*/
          c.set("user_id", user1.getId());
        else
          /*ASSIGN A USER TO CELL 2*/
          c.set("user_id", user2.getId());
        /*ASSIGN A GRID TO CELL*/
        c.set("grid_id", board.getId());
        c.save();

        /*CHECK FOR A WINNER FOR NOW*/
        if(doublet.getSecond() >= 0){
          if(doublet.getSecond() == 1)
            gaming.set("winner_id",user1.getId());
          
          if(doublet.getSecond() == 2)
            gaming.set("winner_id",user2.getId());
          
          gaming.set("user1_id",user1.getId());
          gaming.set("user2_id",user2.getId());
          gaming.set("grid_id",board.getId());
          playing = false;
          gaming.save();
        }
        if (doublet.getSecond() == -2)
        move++;
      }
      board.show();
    }
    /*CHECK THIS PART*/

    /*CREATE A NEW CONECCTION BETWEN GAME A USER, SO, HERE WE SAVE A USER GAME AND CREATE TWO GAME USER. ONE FOR THE PLAYER NUMER ONE
    AN THE OTHER FOR THE PLAYER TWO, INTO A GAME_USER THIS CONTAIN SAME GRID ID, BUT DISTINT GAMEUSER_ID AN PLAYER_ID*/

    Rank ranking = new Rank();
    List<Rank> list = ranking.findAll();
    Boolean modify = false;
    Boolean found = false;

    int i = 0; 
    /*IF THE USER EXIST THIS ADD A WONS GAMEN IN THE RANK TABLE*/
    while(i < list.size() && !modify ){
      ranking = list.get(i);
      if(ranking.get("user_id") == gaming.get("winner_id")){
        found = true;
        modify = true;
        int wons = (int)ranking.get("games_won");
        ranking.set("games_won",wons++);
      }
      i++;
    }

    if(!found){
      ranking = new Rank();
      ranking.set("user_id",gaming.get("winner_id"));
      ranking.set("games_won",1);
      ranking.save();
    }


    i =list.size();

    while(i >1 && modify ){
      modify = false;
      Rank ranking1 = list.get(i);
      Rank ranking2 = list.get(i-1);

      if((int)ranking1.get("games_won") < (int)ranking2.get("games_won")){
        modify = true;
        int id = (int)ranking2.get("user_id");
        int won = (int)ranking2.get("games_won");

        ranking2.set("games_won",ranking1.get("games_won"));
        ranking2.set("user_id",ranking1.get("user_id"));
        ranking2.save();

        ranking1.set("games_won",won);
        ranking1.set("user_id",id);
        ranking1.save();

      }
      i--;
    }

    System.out.println(" LISTA DE RANKINNNGGG EL TAMAÑO ES = " + list.size());
    
    GamesUsers gamePlay = new GamesUsers();
    gamePlay.set("game_id",gaming.getId());
    gamePlay.set("user_id",user1.getId());
    gamePlay.save();

    GamesUsers gamePlay1 = new GamesUsers();
    gamePlay1.set("game_id",gaming.getId());
    gamePlay1.set("user_id",user2.getId());
    gamePlay1.save();

    Base.close();
    }
}

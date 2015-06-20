package com.unrc.app;

import static spark.Spark.*;
import com.unrc.app.User;
import org.javalite.activejdbc.Base;
import java.util.Scanner;
import java.util.*;
import java.lang.Object;

import spark.ModelAndView;
import spark.TemplateEngine;


public class App{

  public static void main(String[] args) {
    externalStaticFileLocation("./media");

    before((request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
    });

    get("/main", (request, response) -> {
      return new ModelAndView(null, "main.moustache");
    },
      new MustacheTemplateEngine()
    );

    get("/users", (request, response) -> {
        Map<String, Object > attributes = new HashMap<>();
        List<User> users = User.findAll();
        attributes.put("users", users);
        return new ModelAndView(attributes, "users.moustache");
      },
      new MustacheTemplateEngine()
    );


    post("/add_token",(request,response)-> {
      Map<String, Object> attributes = new HashMap<>();
      
      Integer column = Integer.parseInt(request.queryParams("col"));
      Integer gameID = Integer.parseInt(request.queryParams("grid_id"));
      Game currentGame = new Game();
      Grid currentGrid = new Grid();

      currentGame = currentGame.findFirst("id = "+gameID);
      currentGrid = currentGrid.findFirst("id = "+ currentGame.get("grid_id"));
      Cell cell = new Cell();
      List<Cell> listCells = cell.where("grid_id = ?",currentGrid.getId());
      int move = 0;
      move = currentGrid.load(listCells);
      Doublet doublet = currentGrid.play(move%2+1,column);

      cell.set("pos_x", doublet.getFirst());
      cell.set("pos_y", column);
      if(move%2 == 0)
        /*ASSIGN A USER TO CELL 1*/
        cell.set("user_id", currentGame.get("user1_id"));
      else
        /*ASSIGN A USER TO CELL 2*/
        cell.set("user_id", currentGame.get("user2_id"));
      /*ASSIGN A GRID TO CELL*/
      cell.set("grid_id", currentGrid.getId());
      cell.save();

      response.redirect("/play");
      return null;

    }
    );




    get("/user/new", (request, response) -> {
      return new ModelAndView(null, "formulario.moustache");
    }, 
      new MustacheTemplateEngine()
    );



    get("/user/:id", (request, response) -> {
      
      Map<String, Object > attributes = new HashMap<>();
      
      User user = User.findFirst("id = " + request.params(":id"));
      // System.out.println(user.toStringFirstName());
      attributes.put("user",user);
      
      return new ModelAndView(attributes, "user.moustache");
    },
      new MustacheTemplateEngine()
    );




    // post("/add_token/:col",(request,response)-> {
    //   Integer col = request.queryParams("col");
    
    // });

    post("/user/create", (request, response) -> {
    
      String name     = (String)request.queryParams("user[firstname]");
      String email    = (String)request.queryParams("user[lastname]");
      String lastname = (String)request.queryParams("user[email]");

      User user12 = new User();
      user12.createUser(email, name, lastname);
      
      response.redirect("/user/" + user12.getId());
      return null;
     });

    get("/grids",(request, response) -> {

      Map<String, Object> attributes = new HashMap<>();
      List<Grid> grids = Grid.findAll();
      attributes.put("grids", grids);
      return new ModelAndView(attributes, "grids.moustache");
    },
      new MustacheTemplateEngine()
   );



    get("/games",(request, response) -> {

      Map<String, Object> attributes = new HashMap<>();
      List<Game> games = Game.findAll();
      attributes.put("games", games);
      return new ModelAndView(attributes, "games.moustache");
    },
      new MustacheTemplateEngine()
   );

  get("/ranks",(request, response) -> {

      Map<String, Object> attributes = new HashMap<>();
      List<Rank> ranks = Rank.findAll();
      attributes.put("ranks", ranks);
      return new ModelAndView(attributes, "ranks.moustache");
    },
      new MustacheTemplateEngine()
   );

    get("/gamesusers",(request, response) -> {

      Map<String, Object> attributes = new HashMap<>();
      List<GamesUsers> gamesusers = GamesUsers.findAll();
      attributes.put("gamesusers", gamesusers);
      return new ModelAndView(attributes, "gamesusers.moustache");
    },
      new MustacheTemplateEngine()
   );

    get("/play", (request, response) -> {
      // crear para que el mustache tome la ficah y la pued alelgar a mostrar
      // luego null sera repmplazado por parametros
      Map<String, Object> attributes = new HashMap<>();
      Grid grid = new Grid();
      attributes.put("grid", grid);
      return new ModelAndView(attributes, "play.moustache");
       // return new ModelAndView(null, "play.moustache");

    },
      new MustacheTemplateEngine()
    );

    get("/playNewGame", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
          List<User> users = User.findAll();
          attributes.put("users", users);
      return new ModelAndView(attributes, "playNewGame.moustache");
    },
      new MustacheTemplateEngine()
    );

    get("/returnGame", (request, response) -> {
      return new ModelAndView(null, "returnGame.moustache");
    },
      new MustacheTemplateEngine()
    );

    after((request, response) -> {
      Base.close();
    });

  }

}

// aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

// public class App
// {
//   public static void main( String[] args ){
//     System.out.println( "Hello World!" );

//     Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");

//     // User.deleteAll();
//     // Cell.deleteAll();
//     // Grid.deleteAll();
//     User user1 = new User();
//     User user2 = new User();
//     Scanner in = new Scanner(System.in);

//     System.out.println("PRESS 1 TO CREATE THE USER 1\nPRESS 2 TO SEARCH THE USER 1"); 
//     String number = in.next();
//     /*CREATE USER 1*/
//     if(number.charAt(0) == '2')
//       user1 = user1.getUser();
//     else
//       user1 = user1.createUser();

//     System.out.println("PRESS 1 TO CREATE THE USER 2\nPRESS 2 TO SEARCH THE USER 2");
//     number = in.next();
//     /*CREATE USER 2*/
//     if(number.charAt(0) == '2')
//       user2 = user2.getUser();
//     else
//       user2 = user2.createUser();

//     /*VAR GAME CONDITION*/
//     boolean playing = true;
//     /*CREATE A BOARD*/
//     Grid board = new Grid(6,7);
//     board.save();

//     int move = 0;
//     int row;
//     /*METHOD DATA ENTRY*/
//    // Scanner in = new Scanner(System.in);
//     int column;
//     String columnString;
//     /*CREATE A GAME*/
//     Game gaming = new Game();
//     board.show();
//     while(playing){

//       System.out.println("Player "+(move%2+1)+":");
//       columnString = in.next();
//       //System.out.println(columnString=='g');
//       if (columnString.charAt(0)=='g' || columnString.charAt(0)=='G'){
//         gaming.set("user1_id",user1.getId());
//         gaming.set("user2_id",user2.getId());
//         gaming.set("grid_id",board.getId());
//         playing = false;
//         gaming.save();
//       }
//       else{
//         /*CREATE A CELL*/
//         Cell c = new Cell();
//         /*CONVERT A STRING TO INT*/
//         column = Integer.parseInt(columnString);
//         /*DOUBLET = (FILA,RESULT)*/
//         Doublet doublet = board.play(move%2+1,column);
//         c.set("pos_x", doublet.getFirst());
//         c.set("pos_y", column);
//         if(move%2 == 0)
//           /*ASSIGN A USER TO CELL1*/
//           c.set("user_id", user1.getId());
//         else
//           /*ASSIGN A USER TO CELL 2*/
//           c.set("user_id", user2.getId());
//         /*ASSIGN A GRID TO CELL*/
//         c.set("grid_id", board.getId());
//         c.save();

//         /*CHECK FOR A WINNER FOR NOW*/
//         if(doublet.getSecond() >= 0){
//           if(doublet.getSecond() == 1)
//             gaming.set("winner_id",user1.getId());
          
//           if(doublet.getSecond() == 2)
//             gaming.set("winner_id",user2.getId());
          
//           gaming.set("user1_id",user1.getId());
//           gaming.set("user2_id",user2.getId());
//           gaming.set("grid_id",board.getId());
//           playing = false;
//           gaming.save();
//         }
//         if (doublet.getSecond() == -2)
//         move++;
//       }
//       board.show();
//     }
//     /*CHECK THIS PART*/

//     /*CREATE A NEW CONECCTION BETWEN GAME A USER, SO, HERE WE SAVE A USER GAME AND CREATE TWO GAME USER. ONE FOR THE PLAYER NUMER ONE
//     AN THE OTHER FOR THE PLAYER TWO, INTO A GAME_USER THIS CONTAIN SAME GRID ID, BUT DISTINT GAMEUSER_ID AN PLAYER_ID*/

//     Rank ranking = new Rank();
//     List<Rank> list = ranking.findAll();
//     Boolean modify = false;
//     Boolean found = false;

//     int i = 0; 
//     /*IF THE USER EXIST THIS ADD A WONS GAMEN IN THE RANK TABLE*/
//     while(i < list.size() && !modify ){
//       ranking = list.get(i);
//       if(ranking.get("user_id") == gaming.get("winner_id")){
//         found = true;
//         modify = true;
//         int wons = (int)ranking.get("games_won");
//         ranking.set("games_won",wons++);
//       }
//       i++;
//     }

//     if(!found){
//       ranking = new Rank();
//       ranking.set("user_id",gaming.get("winner_id"));
//       ranking.set("games_won",1);
//       ranking.save();
//     }


//     i =list.size();

//     while(i >1 && modify ){
//       modify = false;
//       Rank ranking1 = list.get(i);
//       Rank ranking2 = list.get(i-1);

//       if((int)ranking1.get("games_won") < (int)ranking2.get("games_won")){
//         modify = true;
//         int id = (int)ranking2.get("user_id");
//         int won = (int)ranking2.get("games_won");

//         ranking2.set("games_won",ranking1.get("games_won"));
//         ranking2.set("user_id",ranking1.get("user_id"));
//         ranking2.save();

//         ranking1.set("games_won",won);
//         ranking1.set("user_id",id);
//         ranking1.save();

//       }
//       i--;
//     }

//     System.out.println(" LISTA DE RANKINNNGGG EL TAMAÑO ES = " + list.size());
    
//     GamesUsers gamePlay = new GamesUsers();
//     gamePlay.set("game_id",gaming.getId());
//     gamePlay.set("user_id",user1.getId());
//     gamePlay.save();

//     GamesUsers gamePlay1 = new GamesUsers();
//     gamePlay1.set("game_id",gaming.getId());
//     gamePlay1.set("user_id",user2.getId());
//     gamePlay1.save();

//     Base.close();
//     }
// }


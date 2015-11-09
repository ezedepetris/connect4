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

    /*open the connection with the database*/
    before((request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
    });

    /*return the menu of the application*/
    get("/main", (request, response) -> {
      return new ModelAndView(null, "main.moustache");
    },
      new MustacheTemplateEngine()
    );

    get("/playNewGame", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
          request.session(true);
      response.redirect("/register_1");
      return null;
    });

    /*return the form to register the user 1*/
    get("/register_1", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
      return new ModelAndView(null, "register1.moustache");
    },
      new MustacheTemplateEngine()
    );
    /*return the form to register the user 2*/
    get("/register_2", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
      return new ModelAndView(null, "register2.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*search or create an user*/
    post("/register1", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
      String fname1 = request.queryParams("fname1");
      String lname1 = request.queryParams("lname1");
      String email1 = request.queryParams("email1");
      if(email1==null || email1==""){
        response.redirect("/main");
        return null;
      }
      User user1 = new User();
      user1 = user1.getUser(email1);
      Long longUser1_id;
      Integer intUser1_id;
      if(user1 == null){
        if(fname1==null|| lname1 ==null||fname1==""|| lname1 ==""){
          response.redirect("/main");
          return null;
        }
        User aux = new User(email1,fname1,lname1);
        user1 = aux;
        longUser1_id = (Long)user1.getId();
        request.session().attribute("user1long",longUser1_id);
      }
      else{
        intUser1_id = (Integer)user1.getId();
        request.session().attribute("user1int",intUser1_id);
      }
      response.redirect("/register_2");
      return null;
    });

    /*search or create the user 2, and create a new game */
    post("/register2", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
      String fname2 = request.queryParams("fname2");
      String lname2 = request.queryParams("lname2");
      String email2 = request.queryParams("email2");
      if(email2==null){
        response.redirect("/main");
        return null;
      }
      User user2 = new User();
      user2 = user2.getUser(email2);
      Long longUser2_id;
      Integer intUser2_id;
      if(user2 == null){
        if(fname2 ==null|| lname2 ==null || fname2 ==""|| lname2 ==""){
          response.redirect("/main");
          return null;
        }
        User aux = new User(email2,fname2,lname2);
        user2 = aux;
        longUser2_id = (Long)user2.getId();
        request.session().attribute("user2",longUser2_id);
      }
      else{
        intUser2_id = (Integer)user2.getId();
      }

      Game newGame = new Game();
      Grid newGrid = new Grid();
      newGrid.save();
      if(request.session().attribute("user1int")!=null)
        newGame.set("user1_id",(Integer)request.session().attribute("user1int"));
      else
        newGame.set("user1_id",(Long)request.session().attribute("user1long"));
      newGame.set("user2_id",user2.getId());
      newGame.set("grid_id",newGrid.getId());
      newGame.save();
      request.session().attribute("gameId",newGame.getId());
      response.redirect("/play");
      return null;
    }

    );


      /*show the board*/
     get("/play", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();

      Long longGameID = null;
      Integer intGameID = null;
      if(request.session().attribute("returnGame")== null)
        longGameID = (Long)request.session().attribute("gameId");
      else{
        intGameID = (Integer)request.session().attribute("gameId");
        // request.session().removeAttribute("returnGame");
      }


      // Integer gameID = 61;//hardcode
      Game currentGame = new Game();
      Grid currentGrid = new Grid();

      if (longGameID != null)
        currentGame = currentGame.findFirst("id = "+longGameID);
      else
        currentGame = currentGame.findFirst("id = "+intGameID);
      currentGrid = currentGrid.findFirst("id = "+ (int)currentGame.get("grid_id"));
      Cell cell = new Cell();
      List<Cell> listCells = cell.where("grid_id = ?",(int)currentGrid.getId());
      currentGrid.load(listCells);

      attributes.put("grid", currentGrid);
      return new ModelAndView(attributes, "play.moustache");

    },
      new MustacheTemplateEngine()
    );
    
    /*show all the user of the application*/
    get("/users", (request, response) -> {
        Map<String, Object > attributes = new HashMap<>();
        List<User> users = User.findAll();
        attributes.put("users", users);
        return new ModelAndView(attributes, "users.moustache");
      },
      new MustacheTemplateEngine()
    );

    /*insert an atoken and verify if there is a winner or deat head */
    post("/add_token",(request,response)-> {
      Map<String, Object> attributes = new HashMap<>();
      
      Integer column = Integer.parseInt(request.queryParams("col"));

      Long longGameID = null;
      Integer intGameID = null;
      if(request.session().attribute("returnGame")== null)
        longGameID = (Long)request.session().attribute("gameId");
      else{
        intGameID = (Integer)request.session().attribute("gameId");
      }


      Game currentGame = new Game();
      Grid currentGrid = new Grid();

      if (longGameID != null)
        currentGame = currentGame.findFirst("id = "+longGameID);
      else
        currentGame = currentGame.findFirst("id = "+intGameID);

      currentGrid = currentGrid.findFirst("id = "+ currentGame.get("grid_id"));
      if(column==0){
        currentGame.save();
        request.session().removeAttribute("returnGame");
        response.redirect("/main");
        return null;
      }
      Cell cell = new Cell();
      List<Cell> listCells = cell.where("grid_id = ?",currentGrid.getId());
      int move = 0;
      move = currentGrid.load(listCells);
      Doublet doublet = currentGrid.play(move%2+1,column);

      cell.set("pos_x", doublet.getFirst());
      cell.set("pos_y", column);
      if(doublet.getSecond()==0){
        currentGame.set("winner_id",0);
        currentGame.save();
        response.redirect("/dead_heat");
          return null;
      }
      else{
        if(doublet.getSecond() >0){
          if(doublet.getSecond() == 1)
            currentGame.set("winner_id", currentGame.get("user1_id"));
          else
            currentGame.set("winner_id", currentGame.get("user2_id"));
          currentGame.save();
          Rank rank = new Rank();
          rank.upDateRank((Integer)currentGame.get("winner_id"));
          request.session().attribute("winnerID",doublet.getSecond());
          response.redirect("/winner");
          return null;
        }
        else{
          if(doublet.getSecond() != (-1)){
            if(move%2 == 0)
              /*ASSIGN A USER TO CELL 1*/
              cell.set("user_id", currentGame.get("user1_id"));
            else
              /*ASSIGN A USER TO CELL 2*/
              cell.set("user_id", currentGame.get("user2_id"));
            /*ASSIGN A GRID TO CELL*/
            cell.set("grid_id", currentGrid.getId());
            cell.save();
          }
          attributes.put("grid", currentGrid);
          return new ModelAndView(attributes, "playAjax.moustache");

        }
      }

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
    /*show all the ranks*/
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

   
   
   get("/grids",(request, response) -> {

      Map<String, Object> attributes = new HashMap<>();
      List<Grid> grids = Grid.findAll();
      attributes.put("grids", grids);
      return new ModelAndView(attributes, "grids.moustache");
    },
      new MustacheTemplateEngine()
   );

   /*return the view when an user wins*/
    get("/winner", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
      // User user = User.where("id = ?", request.session().attribute("user_id"));
      attributes.put("player", request.session().attribute("winnerID"));
      return new ModelAndView(attributes, "winner.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*return the view when a game is dead heat*/
    get("/dead_heat", (request, response) -> {
      return new ModelAndView(null, "dead_heat.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*here search two useres and load his last game*/
    get("/returnGame", (request, response) -> {
      return new ModelAndView(null, "returnGame.moustache");
    },
      new MustacheTemplateEngine()
    );
    
    get("/choiseAdversary",(request, response) -> {
      return new ModelAndView(null, "choiseAdversary.moustache");
    },
      new MustacheTemplateEngine()
   );



    get("/createFakeGame",(request, response) -> {
      Map<String, Object> attributes = new HashMap<>();

      User user1 = new User();
      User user2 = new User();
      user1 = user1.getUser("human@human.com");
      user2 = user2.getUser("computer@computer.com");

      if(user1 == null){
        user1 = new User("human@human.com","CEO","ERROR-404");
      }

      if(user2 == null){
        user2 = new User("computer@computer.com","CEO","ERROR-404");
      }

      Game newGame = new Game();
      Grid newGrid = new Grid();
      newGrid.save();
 
      newGame.set("user1_id",user1.getId());
      newGame.set("user2_id",user2.getId());
      newGame.set("grid_id",newGrid.getId());
      newGame.save();
      request.session().attribute("gameId",newGame.getId());

      response.redirect("/play");
      return null;
    }
   );


    post("/findGame", (request, response) -> {
      String email1 = (String)request.queryParams("email1");
      String email2 = (String)request.queryParams("email2");
      if(email1 == email2 || email1 == null || email2 == null || email1=="" || email2==""){
        response.redirect("/main");
        return null;
      }
      User player1 = User.findFirst("email = ?" ,email1);
      User player2 = User.findFirst("email = ?" ,email2);
       if(player1 == null || player2 == null){
        response.redirect("/main");
        return null;
      }
      Game game = Game.findFirst("((user1_id = "+ (Integer)player1.getId() +" and user2_id = "+ (Integer)player2.getId()+") or (user2_id = "+ (Integer)player1.getId() +" and user1_id = "+ (Integer)player2.getId()+")) and (winner_id <=> null)");
      System.out.println("PASEERERERERERE ER RE RERE RE RERER KEK EKRE KER E");
      if(game==null){
        response.redirect("/main");
        return null;
      }
      if (game == null)
        return new ModelAndView(null, "main.moustache");
      else{
        Integer game_id = (Integer)game.getId();
        request.session().attribute("gameId", game_id);
        request.session().attribute("returnGame", 0);
        response.redirect("/play");
        return null;
      }
    });


    /*close the connection*/  
    after((request, response) -> {
      Base.close();
    });

  }

}



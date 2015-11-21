package com.unrc.app;

import static spark.Spark.*;
import com.unrc.app.User;
import org.javalite.activejdbc.Base;
import java.util.Scanner;
import java.util.*;
import java.net.*;

import java.lang.Object;

import spark.ModelAndView;
import spark.TemplateEngine;


public class App{
  private static String getServerIp(){
        String ip="";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    System.out.println(iface.getDisplayName() + " " + ip);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
      return ip;
    }



  public static void main(String[] args) {
    externalStaticFileLocation("./media");

    /*open the connection with the database*/
    // before((request, response) -> {
    //   Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
    // });

    /*return the view for register a new user or login of the application*/
    get("/firstView", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");

      Variable.computerGame = false;
      request.session(true);
      Base.close();

      return new ModelAndView(null, "firstView.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*return the view for sing in a user*/
    get("/singIn", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Base.close();

      return new ModelAndView(null, "singIn.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*return the view for sing un a user*/
    get("/singUp", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Base.close();

      return new ModelAndView(null, "singUp.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*return the menu of the application*/
    get("/main", (request, response) -> {
      //Base.close();
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Map<String, Object> attributes = new HashMap<>();
      request.session().removeAttribute("gameId");
      request.session().removeAttribute("winnerID");
      Variable.computerGame = false;
      Integer user_int;
      Long user_long;
      User loginUser = new User();
      if(request.session().attribute("user1int")!=null){
        user_int = (Integer)request.session().attribute("user1int");
        loginUser = loginUser.getUserInteger(user_int);
      }
      else{
        user_long = (Long)request.session().attribute("user1long");
        loginUser = loginUser.getUserLong(user_long);
      }
      attributes.put("user",loginUser);
      Base.close();

      return new ModelAndView(attributes, "main.moustache");
    },
      new MustacheTemplateEngine()
    );

    // get("/playNewGame", (request, response) -> {
    //   Map<String, Object> attributes = new HashMap<>();
    //       request.session(true);

    //   response.redirect("/register_2");
    //   return null;
    // });

    /*return the menu of the application*/
    get("/playNewGame", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Base.close();

      return new ModelAndView(null, "firstViewP2.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*return the view for sing in a user 2*/
    get("/singInP2", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Base.close();

      return new ModelAndView(null, "singInP2.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*return the view for sing un a user 2*/
    get("/singUpP2", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Base.close();

      return new ModelAndView(null, "singUpP2.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*return the form to register the user 1*/
    get("/register_1", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Map<String, Object> attributes = new HashMap<>();
      Base.close();

      return new ModelAndView(null, "register1.moustache");
    },
      new MustacheTemplateEngine()
    );
    /*return the form to register the user 2*/
    get("/register_2", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Map<String, Object> attributes = new HashMap<>();
      Base.close();

      return new ModelAndView(null, "register2.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*search or create an user*/
    post("/register1", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Map<String, Object> attributes = new HashMap<>();
      String fname1 = request.queryParams("fname1");
      String lname1 = request.queryParams("lname1");
      String email1 = request.queryParams("email1");

      if(email1==null || email1==""){
        Base.close();
        response.redirect("/main");
        return null;
      }
      User user1 = new User();
      user1 = user1.getUser(email1);
      Long longUser1_id;
      Integer intUser1_id;
      if(user1 == null){
        if(fname1==null|| lname1 ==null||fname1==""|| lname1 ==""){
          Base.close();
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
      Base.close();
      response.redirect("/main");
      return null;
    });

    /*search or create the user 2, and create a new game */
    post("/register2", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Map<String, Object> attributes = new HashMap<>();
      String fname2 = request.queryParams("fname2");
      String lname2 = request.queryParams("lname2");
      String email2 = request.queryParams("email2");
      if(email2==null){
        Base.close();
        response.redirect("/main");
        return null;
      }
      User user2 = new User();
      user2 = user2.getUser(email2);
      Long longUser2_id;
      Integer intUser2_id;
      if(user2 == null){
        if(fname2 ==null|| lname2 ==null || fname2 ==""|| lname2 ==""){
          Base.close();
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
      Base.close();

      response.redirect("/play");
      return null;
    }

    );


      /*show the board*/
    get("/play", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
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
      attributes.put("ip", App.getServerIp());
      if(Variable.computerGame)
        attributes.put("computer", 1);
      else
        attributes.put("computer", 2);  

      Base.close();
      return new ModelAndView(attributes, "play.moustache");

    },
      new MustacheTemplateEngine()
    );
    
    /*show all the user of the application*/
    get("/users", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Map<String, Object> attributes = new HashMap<>();
      List<User> users = User.findAll();
      attributes.put("users", users);

      Base.close();
      return new ModelAndView(attributes, "users.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*insert an atoken and verify if there is a winner or deat head */
            
    post("/add_token",(request,response)-> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Map<String, Object> attributes = new HashMap<>();
      if(request.session().attribute("winnerID")!= null){
        Base.close();

        response.redirect("/winner");
        return null;
      }
      
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
        Base.close();

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
        attributes.put("computer", 2);
        Base.close();
        return new ModelAndView(attributes,"dead_heat.moustache");

        // response.redirect("/dead_heat");
        // return null;
      }
      else{
        if(doublet.getSecond() >0){
          if(doublet.getSecond() == 1)
            currentGame.set("winner_id", currentGame.get("user1_id"));
          else
            currentGame.set("winner_id", currentGame.get("user2_id"));
          currentGame.save();
          if(!Variable.computerGame){
            Rank rank = new Rank();
            rank.upDateRank((Integer)currentGame.get("winner_id"));
          }
          if(Variable.computerGame){
            attributes.put("winnerID","human");
            request.session().attribute("winnerID","human");
          }
          else
            attributes.put("winnerID","player "+doublet.getSecond());
          attributes.put("computer", 2);
          Base.close();

          return new ModelAndView(attributes,"winner.moustache");

          // response.redirect("/winner");
          // return null;
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

          // HERE THE COMPUTER BEGINS TO PLAY BITCHESS!!!
          // if(Variable.computerGame){ 

          //   Integer nextMove = Variable.engine.computeSuccessor(currentGrid);

          //   doublet = currentGrid.play(2,nextMove+1);

          //   Cell cellComputer = new Cell();
          //   cellComputer.set("pos_x", doublet.getFirst());
          //   cellComputer.set("pos_y", nextMove+1);
          //   if(doublet.getSecond()==0){
          //     currentGame.set("winner_id",0);
          //     currentGame.save();
          //     return new ModelAndView(null,"dead_heat.moustache");
          //     // response.redirect("/dead_heat");
          //     // return null;
          //   }
          //   else{
          //     if(doublet.getSecond() >0){
          //       currentGame.set("winner_id", currentGame.get("user2_id"));
          //       currentGame.save();
          //       attributes.put("winnerID","COMPUTER");
          //       return new ModelAndView(attributes,"winner.moustache");
          //       // response.redirect("/winner");
          //       // return null;
          //     }
          //     else{
          //       if(doublet.getSecond() != (-1)){
          //         cellComputer.set("user_id", currentGame.get("user2_id"));
          //         /*ASSIGN A GRID TO CELLComputer*/
          //         cellComputer.set("grid_id", currentGrid.getId());
          //         cellComputer.save();
          //       }          
          //     }
          //   }
          // }
          attributes.put("grid", currentGrid);
          attributes.put("ip", App.getServerIp());
          if(Variable.computerGame)
            attributes.put("computer", 1);
          else
            attributes.put("computer", 2);  

          
          if(Variable.computerGame){
            Base.close();
            return new ModelAndView(attributes, "playAjaxComputer.moustache");
          }
          else{
            Base.close();
            return new ModelAndView(attributes, "playAjax.moustache");
          }
        }
      }

    },
      new MustacheTemplateEngine() 
    );



    post("/add_token_computer",(request,response)-> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Map<String, Object> attributes = new HashMap<>();

      if(request.session().attribute("winnerID")!= null){
        Base.close();
        response.redirect("/winner");
        return null;
      }

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
     
      Cell cellComputer = new Cell();
      List<Cell> listCells = cellComputer.where("grid_id = ?",currentGrid.getId());
      currentGrid.load(listCells);

      Integer nextMove = Variable.engine.computeSuccessor(currentGrid);

      Doublet doublet = currentGrid.play(2,nextMove+1);

      cellComputer.set("pos_x", doublet.getFirst());
      cellComputer.set("pos_y", nextMove+1);

      if(doublet.getSecond()==0){
        currentGame.set("winner_id",0);
        currentGame.save();
        attributes.put("computer", 2);
        Base.close();

        return new ModelAndView(attributes,"dead_heat.moustache");
        // response.redirect("/dead_heat");
        // return null;
      }
      else{
        if(doublet.getSecond() >0){
          currentGame.set("winner_id", currentGame.get("user2_id"));
          currentGame.save();
          attributes.put("winnerID","COMPUTER");
          Base.close();

          return new ModelAndView(attributes,"winner.moustache");
          // response.redirect("/winner");
          // return null;
        }
        else{
          if(doublet.getSecond() != (-1)){
            cellComputer.set("user_id", currentGame.get("user2_id"));
            /*ASSIGN A GRID TO CELLComputer*/
            cellComputer.set("grid_id", currentGrid.getId());
            cellComputer.save();
          }          
        }
      }
      attributes.put("grid", currentGrid);
      attributes.put("ip", App.getServerIp());
      attributes.put("computer", 1);  
      Base.close();
      return new ModelAndView(attributes, "playAjax.moustache");
      },
      new MustacheTemplateEngine()
      );










    get("/games",(request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");

      Map<String, Object> attributes = new HashMap<>();
      List<Game> games = Game.findAll();
      attributes.put("games", games);
      Base.close();
      return new ModelAndView(attributes, "games.moustache");
    },
      new MustacheTemplateEngine()
    );
    /*show all the ranks*/
    get("/ranks",(request, response) -> {
     Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");

      Map<String, Object> attributes = new HashMap<>();
      List<Rank> ranks = Rank.findAll();
      attributes.put("ranks", ranks);
      Base.close();
      return new ModelAndView(attributes, "ranks.moustache");
    },
        new MustacheTemplateEngine()
    );

    get("/gamesusers",(request, response) -> {
      //Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");

      Map<String, Object> attributes = new HashMap<>();
      List<GamesUsers> gamesusers = GamesUsers.findAll();
      attributes.put("gamesusers", gamesusers);
      Base.close();
      return new ModelAndView(attributes, "gamesusers.moustache");
    },
      new MustacheTemplateEngine()
    );

   
   
    get("/grids",(request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");

      Map<String, Object> attributes = new HashMap<>();
      List<Grid> grids = Grid.findAll();
      attributes.put("grids", grids);
      Base.close();

        return new ModelAndView(attributes, "grids.moustache");
    },
        new MustacheTemplateEngine()
    );

   /*return the view when an user wins*/
    get("/winner", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Map<String, Object> attributes = new HashMap<>();
      // User user = User.where("id = ?", request.session().attribute("user_id"));
      attributes.put("winnerID", request.session().attribute("winnerID"));
      Base.close();

      return new ModelAndView(attributes, "winner.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*return the view when a game is dead heat*/
    get("/dead_heat", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Base.close();

      return new ModelAndView(null, "dead_heat.moustache");
    },
      new MustacheTemplateEngine()
    );

    /*here search two useres and load his last game*/
    get("/returnGame", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Base.close();

      return new ModelAndView(null, "returnGame.moustache");
    },
      new MustacheTemplateEngine()
    );
    
    get("/choiseAdversary",(request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Base.close();

      return new ModelAndView(null, "choiseAdversary.moustache");
    },
      new MustacheTemplateEngine()
    );



    post("/createFakeGame",(request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Map<String, Object> attributes = new HashMap<>();
      Variable.computerGame = true;
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

      Base.close();
      return new ModelAndView(null, "difficulty.moustache");
    },
       new MustacheTemplateEngine()
    );

    post("/difficulty",(request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      Map<String, Object> attributes = new HashMap<>();
      Grid grid = new Grid();
      Integer depth = Integer.parseInt(request.queryParams("level"));
      Variable.engine = new MinMaxEngine(grid,depth);
      System.out.println("THE DEPTH ISs "+ Variable.engine.getMaxDepth());

      Base.close();
      response.redirect("/play");
      return null;
    }
    );


    post("/findGame", (request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
      String email2 = (String)request.queryParams("email2");
      if(email2 == null || email2==""){  
        Base.close();
        response.redirect("/main");
        return null;
      }

      Integer user_int;
      Long user_long;
      User player1 = new User();
      if(request.session().attribute("user1int")!=null){
        user_int = (Integer)request.session().attribute("user1int");
        player1 = player1.getUserInteger(user_int);
      }
      else{
        user_long = (Long)request.session().attribute("user1long");
        player1 = player1.getUserLong(user_long);
      }

      User player2 = User.findFirst("email = ?" ,email2);
       if(player1 == null || player2 == null){
        Base.close();
        response.redirect("/main");
        return null;
      }
      Game game = Game.findFirst("((user1_id = "+ (Integer)player1.getId() +" and user2_id = "+ (Integer)player2.getId()+") or (user2_id = "+ (Integer)player1.getId() +" and user1_id = "+ (Integer)player2.getId()+")) and (winner_id <=> null)");
      if(game==null){
        Base.close();
        response.redirect("/main");
        return null;
      }
      if (game == null){
        Base.close();
        return new ModelAndView(null, "main.moustache");
      }
      else{
        Integer game_id = (Integer)game.getId();
        request.session().attribute("gameId", game_id);
        request.session().attribute("returnGame", 0);
        Base.close();
        response.redirect("/play");
        return null;
      }
    });


    /*close the connection*/  
    // after((request, response) -> {
    //   Base.close();
    // });

  }

}



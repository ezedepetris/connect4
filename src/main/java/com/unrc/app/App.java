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

    get("/playNewGame", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
          request.session(true);
      response.redirect("/register_1");
      return null;
    });

    get("/register_1", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
      return new ModelAndView(null, "register1.moustache");
    },
      new MustacheTemplateEngine()
    );

    get("/register_2", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
      return new ModelAndView(null, "register2.moustache");
    },
      new MustacheTemplateEngine()
    );

    post("/register1", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
      String fname1 = request.queryParams("fname1");
      String lname1 = request.queryParams("lname1");
      String email1 = request.queryParams("email1");
      //crear o buscar usuario
      // request.session(true);

      User user1 = new User();
      user1 = user1.getUser(email1);
      Long longUser1_id;
      Integer intUser1_id;
      if(user1 == null){
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

    post("/register2", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
      String fname2 = request.queryParams("fname2");
      String lname2 = request.queryParams("lname2");
      String email2 = request.queryParams("email2");
      //crear o buscar usuario


      User user2 = new User();
      user2 = user2.getUser(email2);
      Long longUser2_id;
      Integer intUser2_id;
      if(user2 == null){
        User aux = new User(email2,fname2,lname2);
        user2 = aux;
        longUser2_id = (Long)user2.getId();
        request.session().attribute("user2",longUser2_id);
      }
      else{
        intUser2_id = (Integer)user2.getId();
      }

      // String user2_id =(String)user2.getId();
      // request.session().attribute("SESSION_NAME",user2.getId());

      Game newGame = new Game();
      Grid newGrid = new Grid();
      newGrid.save();
      System.out.println("EL ID DEL USUARIO 1 ESSSSSSSSSSSS ="+request.session().attribute("user1"));
      if(request.session().attribute("user1int")!=null)
        newGame.set("user1_id",(Integer)request.session().attribute("user1int"));
      else
        newGame.set("user1_id",(Long)request.session().attribute("user1long"));
      newGame.set("user2_id",user2.getId());
      newGame.set("grid_id",newGrid.getId());
      newGame.save();
      request.session().attribute("gameId",newGame.getId());
      // String newGameId = (String) newGame.getId();

      // request.session().attribute(user2_id,user2_id);
      System.out.println("ELLL ATRRIIBUUTOOO OGAMEMEE ESSSSS SS S S SS S = "+ request.session().attribute("gameId"));
      response.redirect("/play");
      return null;
    }

    );



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
      System.out.println(listCells.size()+ " EL TAMAÑO DE LA LISTA DE LAS CELDAS");
      currentGrid.load(listCells);

      attributes.put("grid", currentGrid);
      return new ModelAndView(attributes, "play.moustache");
       // return new ModelAndView(null, "play.moustache");

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

      Long longGameID = null;
      Integer intGameID = null;
      if(request.session().attribute("returnGame")== null)
        longGameID = (Long)request.session().attribute("gameId");
      else{
        intGameID = (Integer)request.session().attribute("gameId");
        // request.session().removeAttribute("returnGame");
      }


      // Long gameID = (Long)request.session().attribute("gameId");
      // Integer gameID = 61;//hardcode
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
          // return new ModelAndView(attributes,"/winner.moustache");
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

          response.redirect("/play");
          return null;
        }
      }

    }
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

   
   
   get("/grids",(request, response) -> {

      Map<String, Object> attributes = new HashMap<>();
      List<Grid> grids = Grid.findAll();
      attributes.put("grids", grids);
      return new ModelAndView(attributes, "grids.moustache");
    },
      new MustacheTemplateEngine()
   );


    get("/winner", (request, response) -> {
      Map<String, Object> attributes = new HashMap<>();
      // User user = User.where("id = ?", request.session().attribute("user_id"));
      attributes.put("player", request.session().attribute("winnerID"));
      return new ModelAndView(attributes, "winner.moustache");
    },
      new MustacheTemplateEngine()
    );






     get("/dead_heat", (request, response) -> {
      return new ModelAndView(null, "dead_heat.moustache");
    },
      new MustacheTemplateEngine()
    );


   







    get("/returnGame", (request, response) -> {
      return new ModelAndView(null, "returnGame.moustache");
    },
      new MustacheTemplateEngine()
    );
    
    post("/findGame", (request, response) -> {
      String email1 = (String)request.queryParams("email1");
      String email2 = (String)request.queryParams("email2");
      User player1 = User.findFirst("email = ?" ,email1);
      User player2 = User.findFirst("email = ?" ,email2);
      Game game = Game.findFirst("user1_id = ?", player1.getId() +" and "+ "user2_id = "+ player2.getId());
      System.out.println("PASEERERERERERE ER RE RERE RE RERER KEK EKRE KER E");
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



    after((request, response) -> {
      Base.close();
    });

  }

}

// package com.unrc.app;

// import static spark.Spark.*;
// import com.unrc.app.User;
// import org.javalite.activejdbc.Base;
// import java.util.Scanner;
// import java.util.*;
// import java.lang.Object;

// import spark.ModelAndView;
// import spark.TemplateEngine;


// public class App{

//   public static void main(String[] args) {
//     externalStaticFileLocation("./media");

//     before((request, response) -> {
//       Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
//     });

//     get("/main", (request, response) -> {
//       return new ModelAndView(null, "main.moustache");
//     },
//       new MustacheTemplateEngine()
//     );

//     get("/playNewGame", (request, response) -> {
//       Map<String, Object> attributes = new HashMap<>();
//           request.session(true);
//       response.redirect("/register_1");
//       return null;
//     });

//     get("/register_1", (request, response) -> {
//       Map<String, Object> attributes = new HashMap<>();
//       return new ModelAndView(null, "register1.moustache");
//     },
//       new MustacheTemplateEngine()
//     );

//     get("/register_2", (request, response) -> {
//       Map<String, Object> attributes = new HashMap<>();
//       return new ModelAndView(null, "register2.moustache");
//     },
//       new MustacheTemplateEngine()
//     );

//     post("/register1", (request, response) -> {
//       Map<String, Object> attributes = new HashMap<>();
//       String fname1 = request.queryParams("fname1");
//       String lname1 = request.queryParams("lname1");
//       String email1 = request.queryParams("email1");
//       //crear o buscar usuario
//       // request.session(true);
//       User user1 = new User();
//       user1 = user1.getUser(email1);
//       if(user1 == null){
//         User aux = new User(email1,fname1,lname1);
//         user1 = aux;
//       }
//       Long user1_id = (Long)user1.getId();
//       request.session().attribute("user1",user1_id);
      
//       response.redirect("/register_2");


//       return null;
//     });

//     post("/register2", (request, response) -> {
//       Map<String, Object> attributes = new HashMap<>();
//       String fname2 = request.queryParams("fname2");
//       String lname2 = request.queryParams("lname2");
//       String email2 = request.queryParams("email2");
//       //crear o buscar usuario


//       User user2 = new User();
//       user2 = user2.getUser(email2);
//       if(user2 == null){
//         User aux = new User(email2,fname2,lname2);
//         user2 = aux;
//       }

//       // String user2_id =(String)user2.getId();
//       // request.session().attribute("SESSION_NAME",user2.getId());

//       Game newGame = new Game();
//       Grid newGrid = new Grid();
//       newGrid.save();
//       System.out.println("EL ID DEL USUARIO 1 ESSSSSSSSSSSS ="+request.session().attribute("user1"));
//       newGame.set("user1_id",(Long)request.session().attribute("user1"));
//       newGame.set("user2_id",user2.getId());
//       newGame.set("grid_id",newGrid.getId());
//       newGame.save();
//       request.session().attribute("gameId",newGame.getId());
//       // String newGameId = (String) newGame.getId();

//       // request.session().attribute(user2_id,user2_id);
//       System.out.println("ELLL ATRRIIBUUTOOO OGAMEMEE ESSSSS SS S S SS S = "+ request.session().attribute("gameId"));
//       return new ModelAndView(null, "play.moustache");
//     },
//       new MustacheTemplateEngine()
//     );










//      get("/play", (request, response) -> {
//       Map<String, Object> attributes = new HashMap<>();


//       Integer gameID = request.session().attribute("gameId");
//       // System.out.println("EELLLLLL DDDDIIIIIIIIAAAAAAAAA  ASDASDAS" + gameI);



//       // Integer gameID = 1;//hardcode
//       Game currentGame = new Game();
//       Grid currentGrid = new Grid();
//       currentGame = currentGame.findFirst("id = "+gameID);
//       currentGrid = currentGrid.findFirst("id = "+ (int)currentGame.get("grid_id"));
//       Cell cell = new Cell();
//       List<Cell> listCells = cell.where("grid_id = ?",(int)currentGrid.getId());
//       System.out.println(listCells.size()+ " EL TAMAÑO DE LA LISTA DE LAS CELDAS");
//       currentGrid.load(listCells);

//       attributes.put("grid", currentGrid);
//       return new ModelAndView(attributes, "play.moustache");
//        // return new ModelAndView(null, "play.moustache");

//     },
//       new MustacheTemplateEngine()
//     );

//     get("/users", (request, response) -> {
//         Map<String, Object > attributes = new HashMap<>();
//         List<User> users = User.findAll();
//         attributes.put("users", users);
//         return new ModelAndView(attributes, "users.moustache");
//       },
//       new MustacheTemplateEngine()
//     );


//     post("/add_token",(request,response)-> {
//       Map<String, Object> attributes = new HashMap<>();
      
//       Integer column = Integer.parseInt(request.queryParams("col"));
//       Integer gameID = (Integer)request.session().attribute("gameId");
//       Game currentGame = new Game();
//       Grid currentGrid = new Grid();

//       currentGame = currentGame.findFirst("id = "+gameID);
//       currentGrid = currentGrid.findFirst("id = "+ currentGame.get("grid_id"));
//       Cell cell = new Cell();
//       List<Cell> listCells = cell.where("grid_id = ?",currentGrid.getId());
//       int move = 0;
//       move = currentGrid.load(listCells);
//       Doublet doublet = currentGrid.play(move%2+1,column);

//       cell.set("pos_x", doublet.getFirst());
//       cell.set("pos_y", column);
//       if(move%2 == 0)
//         /*ASSIGN A USER TO CELL 1*/
//         cell.set("user_id", currentGame.get("user1_id"));
//       else
//         /*ASSIGN A USER TO CELL 2*/
//         cell.set("user_id", currentGame.get("user2_id"));
//       /*ASSIGN A GRID TO CELL*/
//       cell.set("grid_id", currentGrid.getId());
//       cell.save();

//       response.redirect("/play");
//       return null;

//     }
//     );




//     get("/user/:id", (request, response) -> {
      
//       Map<String, Object > attributes = new HashMap<>();
      
//       User user = User.findFirst("id = " + request.params(":id"));
//       // System.out.println(user.toStringFirstName());
//       attributes.put("user",user);
      
//       return new ModelAndView(attributes, "user.moustache");
//     },
//       new MustacheTemplateEngine()
//     );


//     get("/games",(request, response) -> {

//       Map<String, Object> attributes = new HashMap<>();
//       List<Game> games = Game.findAll();
//       attributes.put("games", games);
//       return new ModelAndView(attributes, "games.moustache");
//     },
//       new MustacheTemplateEngine()
//    );

//   get("/ranks",(request, response) -> {

//       Map<String, Object> attributes = new HashMap<>();
//       List<Rank> ranks = Rank.findAll();
//       attributes.put("ranks", ranks);
//       return new ModelAndView(attributes, "ranks.moustache");
//     },
//       new MustacheTemplateEngine()
//    );

//     get("/gamesusers",(request, response) -> {

//       Map<String, Object> attributes = new HashMap<>();
//       List<GamesUsers> gamesusers = GamesUsers.findAll();
//       attributes.put("gamesusers", gamesusers);
//       return new ModelAndView(attributes, "gamesusers.moustache");
//     },
//       new MustacheTemplateEngine()
//    );

   
   
//    get("/grids",(request, response) -> {

//       Map<String, Object> attributes = new HashMap<>();
//       List<Grid> grids = Grid.findAll();
//       attributes.put("grids", grids);
//       return new ModelAndView(attributes, "grids.moustache");
//     },
//       new MustacheTemplateEngine()
//    );




   







//     get("/returnGame", (request, response) -> {
//       return new ModelAndView(null, "returnGame.moustache");
//     },
//       new MustacheTemplateEngine()
//     );

//     after((request, response) -> {
//       Base.close();
//     });

//   }

// }

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


package com.unrc.app;

import static spark.Spark.*;
import com.unrc.app.User;
import org.javalite.activejdbc.Base;
import java.util.Scanner;
import java.util.List;
import java.util.*;

import spark.ModelAndView;
import spark.TemplateEngine;


public class App{

  public static void main(String[] args) {
    externalStaticFileLocation("./Img");

    before((request, response) -> {
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
    });

    get("/main", (request, response) -> {
      return new ModelAndView(null, "main.moustache");
    },
      new MustacheTemplateEngine()
    );

    get("/users", (request, response) -> {
          Map<String, Object> attributes = new HashMap<>();
          List<User> users = User.findAll();
          attributes.put("users_count", users.size());
          attributes.put("users", users);
          return new ModelAndView(attributes, "users.moustache");
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
      return new ModelAndView(null, "play.moustache");
    },
      new MustacheTemplateEngine()
    );

    after((request, response) -> {
      Base.close();
    });

  }

}
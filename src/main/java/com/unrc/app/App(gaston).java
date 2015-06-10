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
    get("/users", (request, response) -> {
    Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");
          Map<String, Object> attributes = new HashMap<>();
          List<User> users = User.findAll();
          attributes.put("users_count", users.size());
          attributes.put("users", users);
          return new ModelAndView(attributes, "users.moustache");
      },
      new MustacheTemplateEngine()
    );
  //Base.close();
  }

}
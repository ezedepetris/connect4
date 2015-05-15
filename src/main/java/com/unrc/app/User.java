package com.unrc.app;

import org.javalite.activejdbc.Model;

public class User extends Model {
   static {
   	//If some of this values aren't entered, the user isn't save it without notification
    validatePresenceOf("first_name");
    validatePresenceOf("last_name");
    validatePresenceOf("email");
  }

}

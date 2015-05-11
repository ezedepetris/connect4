package com.unrc.app;

import com.unrc.app.User;
import org.javalite.activejdbc.Base;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");

        /**User u = new User();
        u.set("first_name", "Ariel");
        u.save();**/

        /**User prueba = new User();
        prueba.set("first_name", "Roberto");
        prueba.set("last_name","Rufini");
        prueba.set("email", "412@nadie.com");
        prueba.save();**/

        User.deleteAll();


        Base.close();
    }
}

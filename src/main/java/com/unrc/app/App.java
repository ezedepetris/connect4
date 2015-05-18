package com.unrc.app;

import com.unrc.app.User;
import org.javalite.activejdbc.Base;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "root", "root");

        User.deleteAll();

        User user1 = new User();
        user1.set("first_name", "ezequiel");
        user1.set("last_name", "depetris");
        user1.set("email","ezedeptris@gmail.com");
        user1.save();

        User user2 = new User();
        user2.set("first_name", "gaston");
        user2.set("last_name", "massimino");
        user2.set("email","gamassimino@gmail.com");
        user2.save();

        boolean playing = true;
        Grid grid = new Grid(6,7);
        int move = 0;
        int row;
        Scanner in = new Scanner(System.in);
        int column;

        while(playing){
            System.out.println("Player "+(move%2+1)+":");
            Cell c = new Cell();
            column = in.nextInt();
            row = grid.play(move%2+1,column);
            c.set("grids_id",grid.getId());
            c.set("pos_x", row);
            c.set("pos_y", column);
            c.set("users_id", move%2+1);
            c.save();
        }

        Base.close();
    }
}

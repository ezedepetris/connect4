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
        // Cell.deleteAll();
        // Grid.deleteAll();

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
        Grid board = new Grid(6,7);
        board.save();
        int move = 0;
        int row;
        Scanner in = new Scanner(System.in);
        int column;

        while(playing){
            System.out.println("Player "+(move%2+1)+":");
            Cell c = new Cell();
            column = in.nextInt();
            row = board.play(move%2+1,column);
            if (row <= 0){
              playing = false;
              row = row * -1;
            }
            c.set("pos_x", row);
            c.set("pos_y", column);
            if(move%2+1 == 0)
              c.set("user_id", user1.getId());
            else
              c.set("user_id", user2.getId());
            c.set("grid_id", board.getId());
            c.save();
            move++;

        }

        GamesUsers partida = new GamesUsers();
        partida.set("grid_id",board.getId());
        partida.set("user_id",user1.getId());
        partida.save();
        Base.close();
    }
}

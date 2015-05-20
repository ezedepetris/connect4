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
       
        Game gaming = new Game();
        while(playing){
            System.out.println("Player "+(move%2+1)+":");
            Cell c = new Cell();
            column = in.nextInt();
            Doublet doublet = board.play(move%2+1,column);
            c.set("pos_x", doublet.getFirst());
            c.set("pos_y", column);
            if(move%2 == 0)
              c.set("user_id", user1.getId());
            else
              c.set("user_id", user2.getId());

            c.set("grid_id", board.getId());
            c.save();

            if(doublet.getSecond() >= 0){
              if(doublet.getSecond() == 1)
                gaming.set("winner_id",user1.getId());
              
              if(doublet.getSecond() == 2)
                gaming.set("winner_id",user2.getId());
              
              gaming.set("user1_id",user1.getId());
              gaming.set("user2_id",user2.getId());
              gaming.set("grid_id",board.getId());
              playing = false;
              gaming.save();
            }
            move++;
        }
        System.out.println("LLLLLEEEEGGGGGGOOOOOOOO"+ gaming.getId());

        GamesUsers gamePlay = new GamesUsers();
        gamePlay.set("game_id",gaming.getId());
        gamePlay.set("user_id",user1.getId());
        gamePlay.save();

        GamesUsers gamePlay1 = new GamesUsers();
        gamePlay1.set("game_id",gaming.getId());
        gamePlay1.set("user_id",user2.getId());
        gamePlay1.save();

        Base.close();
    }
}

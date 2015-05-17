package com.unrc.app;
import org.javalite.activejdbc.Model;

public class Grid extends Model{

	Cell[][] grid;
	private int file;
	private int column;
	private int tokens;

	/*CONSTRUCTOR*/
	/*this method build the structure of an board, and sets the atributs of this method*/
	public Grid(int aFile, int aColumn){
		grid = new Cell[aFile][aColumn];
		file = aFile-1;
		column = aColumn-1;
		tokens = 0;

		for(int i=0; i<=file; i++)
			for (int j=0 ;j<=column; j++)
				grid[i][j]=null;
	}

/*this method search a winner of this game*/
	public int searchWinner(Integer player){
		int count = 0;
		for(int i=0; i<=file; i++){
			count = 0;
			for (int j=0 ;j<column; j++){
				if(grid[i][j]==null||grid[i][j+1] ==null)
					count =0;
				else{
					if(player.compareTo(grid[i][j].getCell()) == 0 && 0 ==player.compareTo(grid[i][j+1].getCell())){
						count++;
						if (count >= 3)
							return player;
					}
					else
						count = 0;
				}
			}	
		}

		for(int i=0; i<=column; i++){
			count = 0;
			for (int j=0 ;j<file; j++){
				if(grid[j][i]==null||grid[j+1][i] ==null)
					count =0;
				else{
					if(player.compareTo(grid[j][i].getCell())  == 0 && 0 == player.compareTo(grid[j+1][i].getCell())){
						count++;
						if (count >= 3)
							return player;
					}
					else
						count = 0;
				}
			}	
		}
		return 0 ;
	}
// 		return 0; //if no body wins, this returns 0, that is equal to said, for doubt no body wins
// 		//return 1 ;//= ganadaron el jugador n 1
// 		// return 2 = ganadaron el jugador n 2


	/*this return true if the board is complete*/
	public Boolean fullBoard(){
		return tokens == (file+1)*(column+1);
	}
	 /*this return true if te column where ther player wish to insert is  full*/
	public Boolean fullColumn(int aColumn){
		return grid[0][aColumn-1] != null;	
	}


	/*this method modeling an user inserting a Cell in the board*/
	public void play(Integer player,int aColumn){
		if (!fullColumn(aColumn))
			putAToken(player,aColumn);
		int game = searchWinner(player);
		if (game == 1)
			System.out.println("EL JUGADRO NUMERO 1 ES EL GANADOR");
		if (game == 2)
			System.out.println("EL JUGADRO NUMERO 2 ES EL GANADOR");
		if(fullBoard())
			System.out.println("EMPATE");
	}

	/*this method show de board at now*/
	public void show(){
		for(int i=0; i<=file; i++){
			for (int j=0 ;j<=column; j++){
				if(grid[i][j]!=null)
					System.out.print(" "+grid[i][j].getCell());
				else
					System.out.print(" 0");
				}	
			System.out.println();
		}
	}

	/*this method modeling how we insert a toke, we choose de dicotomic insert,
	cause its more efficient that lineal form*/
	public void putAToken(Integer player,int aColumn){
		Cell cell = new Cell(player);
		aColumn--;
		int limDown = file;
		int limUp = 0;
		int half;
		Boolean cond = true;
		tokens++;
		while(cond){
			half = (limDown+limUp)/2;

			if(grid[file][aColumn] == null){
				grid[file][aColumn] =cell;
				cond = false;
			}

			if(grid[1][aColumn] != null){
				grid[0] [aColumn]=cell;
				cond = false;
			}
			
			if(grid[half][aColumn]!= null)
				limDown = half;
			else{
				if(grid[half+1][aColumn] == null)
					limUp = half;
				else{
					grid[half][aColumn]= cell; 
					cond = false;
				}
			}
		}
	}
}
	
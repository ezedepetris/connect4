package com.unrc.app;
import org.javalite.activejdbc.Model;


public class Grid extends Model{

	Cell[][] grid;
	private int row;
	private int column;
	private int tokens;

	/*CONSTRUCTOR*/
	/*this method build the structure of an board, and sets the atributs of this method*/
	public Grid(){

	}

	public Grid(int arow, int aColumn){
		grid = new Cell[arow][aColumn];
		row = arow-1;
		column = aColumn-1;
		tokens = 0;

		for(int i=0; i<=row; i++)
			for (int j=0 ;j<=column; j++)
				grid[i][j]=null;
	}

/*this method search a winner of this game*/
	public int searchWinner(Integer player){int count = 0;
		/*this verify the column if some player wins*/
		for(int i=0; i<=row; i++){
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
		/*this verify the row if some player wins*/
		for(int i=0; i<=column; i++){
			count = 0;
			for (int j=0 ;j<row; j++){
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
		/*this verify the diagonal left toright if some player wins*/
		for(int i=0; i<column; i++){
			count = 0;
			int ii = i;
			for (int j=0 ;j<row; j++){

				if(grid[j][ii]==null||grid[j+1][ii+1] ==null)
					count =0;
				else{
					if(player.compareTo(grid[j][ii].getCell())  == 0 && 0 == player.compareTo(grid[j+1][ii+1].getCell())){
						count++;
						ii++;
						if (count >= 3)
							return player;
					}
					else
						count = 0;
				}
			}	
		}
		/*this verify the diagonal right to left if some player wins*/
		for(int i=column; i>0; i--){
			count = 0;
			int ii = i;
			for (int j=0 ;j<row; j++){
				if(grid[j][ii] == null || grid[j+1][ii-1] == null)
					count =0;
				else{
					if(player.compareTo(grid[j][ii].getCell())  == 0 && 0 == player.compareTo(grid[j+1][ii-1].getCell())){
						count++;
						ii--;
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
		return tokens == (row+1)*(column+1);
	}
	 /*this return true if te column where ther player wish to insert is  full*/
	public Boolean fullColumn(int aColumn){
		return grid[0][aColumn-1] != null;	
	}


	/*this method modeling an user inserting a Cell in the board*/
	public Doublet play(Integer player,int aColumn){
		int x = 0;
		Doublet doublet;
		if (!fullColumn(aColumn)){
			x = putACell(player,aColumn);
			x++;
			int game = searchWinner(player);
			if (game == player){
				System.out.println("EL JUGADOR NUMERO "+ player+" ES EL GANADOR");
				return doublet = new Doublet(x,player);
			}
			if(fullBoard()){
				System.out.println("EMPATE");
				doublet = new Doublet(x,0);
				return doublet ;
			}
			return doublet = new Doublet(x,-2);
		}
		return doublet = new Doublet(x,-1);
	}

	/*this method show de board at now*/
	public void show(){
		for(int x = 0; x<25; x++){
			System.out.println();
		}
		for(int i=0; i<=row; i++){
			for (int j=0 ;j<=column; j++){
				System.out.print("\033[34m"+"|");
				if(grid[i][j]!=null){
				//	System.out.println(grid[i][j].getCell());
					if (grid[i][j].getCell()==1){
						System.out.print("\033[31m"+"o"/*grid[i][j].getCell()*/);
						System.out.print("\033[34m"+"");
					}
					else{
						System.out.print("\033[32m"+"x"/*grid[i][j].getCell()*/);
						System.out.print("\033[34m"+"");
					}
				 }
				else{
					System.out.print("\033[30m"+"");
					System.out.print(" ");
					System.out.print("\033[34m"+"");
				}
			}
			System.out.print("|");
			System.out.print("\033[37m"+"");	
			System.out.println();
		}
	}

	/*this method modeling how we insert a toke, we choose de dicotomic insert,
	cause its more efficient that lineal form*/
	public int putACell(Integer player,int aColumn){
		Cell cell = new Cell(player);
		aColumn--;
		int limDown = row;
		int limUp = 0;
		int half = 0;
		Boolean cond = true;
		tokens++;
		while(cond){
			half = (limDown+limUp)/2;

			if(grid[row][aColumn] == null){
				grid[row][aColumn] =cell;
				return row;
			}

			if(grid[1][aColumn] != null){
				grid[0] [aColumn]=cell;
				return 0;
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
		return half;
	}
}
	
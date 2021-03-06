package com.unrc.app;
import org.javalite.activejdbc.Model;
import java.util.*;


public class Grid extends Model{

	Cell[][] grid;
	public int row;
	public int column;
	private int tokens;
	private int level;

	/*CONSTRUCTOR*/
	/*this method build the structure of an board, and sets the atributs of this method*/
	public Grid(){
		grid = new Cell[6][7];
		row = 5;
		column = 6;
		tokens = 0;
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

	public boolean isMin(){
		return level == -1;
	}

	public boolean isMax(){
		return level == 1;
	}

	public int turn(){
		if(tokens%2 == 0)
			return 1;
		return 2;
	}

/*this method search a winner of this game*/
	public int countSequence(Integer player){
		int score = 0;
		int count = 0;
		/*this verify the column if some player wins*/
		for(int i=0; i<=row; i++){
			count = 0;
			for (int j=0 ;j<column; j++){
				if(grid[i][j]==null||grid[i][j+1] ==null){
					if(count == 2)
						score += 10;
					if(count == 3)
						score +=10000;
					if(count == 4)
						score +=1000000;
					count =0;
				}
				else{
					if(player.compareTo(grid[i][j].getCell()) == 0 && 0 ==player.compareTo(grid[i][j+1].getCell())){
						count++;
					}
					else{
						if(count == 2)
							score += 10;
						if(count == 3)
							score +=10000;
						if(count == 4)
							score +=1000000;
						count = 0;
					}
				}
			}	
		}
		/*this verify the row if some player wins*/
		count = 0;
		for(int i=0; i<=column; i++){
			count = 0;
			for (int j=0 ;j<row; j++){
				if(grid[j][i]==null||grid[j+1][i] ==null){
					if(count == 2)
						score += 10;
					if(count == 3)
						score +=10000;
					if(count == 4)
						score +=1000000;
					count =0;
				}
				else{
					if(player.compareTo(grid[j][i].getCell())  == 0 && 0 == player.compareTo(grid[j+1][i].getCell())){
						count++;
					}
					else{
						if(count == 2)
							score += 10;
						if(count == 3)
							score +=10000;
						if(count == 4)
							score +=1000000;
						count = 0;
					}
				}
			}	
		}
		/*this verify the diagonal left toright if some player wins*/
		count = 0;
		for(int i=0; i<column; i++){
			count = 0;
			int ii = i;
			for (int j=0 ;j<row; j++){
				if(ii<column){
					if(grid[j][ii]==null||grid[j+1][ii+1] ==null){
						if(count == 2)
							score += 10;
						if(count == 3)
							score +=1000;
						if(count == 4)
							score +=1000000;
						count =0;
					}
					else{
						if(player.compareTo(grid[j][ii].getCell())  == 0 && 0 == player.compareTo(grid[j+1][ii+1].getCell())){
							count++;
							ii++;
						}
						else{
							if(count == 2)
								score += 10;
							if(count == 3)
								score +=1000;
							if(count == 4)
								score +=1000000;
							count = 0;
						}
					}
				}
			}	
		}
		/*this verify the diagonal right to left if some player wins*/
		count = 0;
		for(int i=column; i>0; i--){
			count = 0;
			int ii = i;
			for (int j=0 ;j<row; j++){
				if(ii>0){
					if(grid[j][ii] == null || grid[j+1][ii-1] == null){
						if(count == 2)
							score += 10;
						if(count == 3)
							score +=10000;
						if(count == 4)
							score +=1000000;
						count =0;
					}
					else{
						if(player.compareTo(grid[j][ii].getCell())  == 0 && 0 == player.compareTo(grid[j+1][ii-1].getCell())){
							count++;
							ii--;
						}
						else{
							if(count == 2)
								score += 10;
							if(count == 3)
								score +=10000;
							if(count == 4)
								score +=1000000;
							count = 0;
						}
					}
				}	
			}
		}
		return score ;
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
				return doublet = new Doublet(x,player);
			}
			if(fullBoard()){
				doublet = new Doublet(x,0);
				return doublet ;
			}
			return doublet = new Doublet(x,-2);
		}
		return doublet = new Doublet(x,-1);
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

	public int value(Grid state){
		int pc = state.countSequence(2);
		int human = state.countSequence(1);
		if(state.isMax())
			return pc;
		else
			return -human;
	}

	/*load a old game*/
	public int load(List<Cell> listCells){
		int move = 0;
		Cell cell = null;
		row = 5;
		column = 6;
		while(move<listCells.size()){
      cell = listCells.get(move);
      int x = (int)cell.get("pos_x");
			int y = (int)cell.get("pos_y");
      grid[(x-1)][(y-1)] = new Cell(move%2+1);
			move++;
    }
    tokens = move;
    return move;
   }

	
/*defines the color for the turn player*/
public String next(){
	if(tokens%2==0)
		return ("<td><h1>TURN<button class="+'"'+"crojo"+'"'+"></button></h1>");
	return ("<td><h1>TURN<button class="+'"'+"cverde"+'"'+"></button></h1>");
}
	
	/*defines the color for a button*/
 public String colorButton(){
 	if(tokens%2==0)
 		return ("<input type="+'"'+"image"+'"'+"src="+'"'+"redHand.png"+'"'+"alt="+'"'+"Submit"+'"'+"width="+'"'+"70"+'"'+"height="+'"'+"70"+'"'+">");
 	else
 		return ("<input type="+'"'+"image"+'"'+"src="+'"'+"greenHand.png"+'"'+"alt="+'"'+"Submit"+'"'+"width="+'"'+"70"+'"'+"height="+'"'+"70"+'"'+">");
 }



	/*this method show the board on the client side*/
	public String menu(){
		if(Variable.computerGame)
			return ("<a href="+'"'+"http://localhost:4567/main"+'"'+"><button type ="+'"'+"button" +'"'+">MENU</button></a>");
		return ("<td width="+'"'+"70"+'"'+ "align="+'"'+"left"+'"'+"><button type ="+'"'+"submit"+'"'+" >SAVE GAME</button></td>");
	}
	public String print(){
		String board = "";

		board +=("<table align="+'"'+"center"+'"'+" bgcolor="+'"'+"#0174DF"+'"'+">");
		for(int i=0; i<=row; i++){
		board +=("<tr>");
			for (int j=0 ;j<=column; j++){
				if(grid[i][j]!=null){
					if (grid[i][j].getCell()==1){
							board +=("<td><button class="+'"'+"crojo"+'"'+"></button>");
							board +=("</td>");
						//imprime rojo
					}
					else{
						board +=("<td><button class="+'"'+"cverde"+'"'+"></button>");
						board +=("</td>");
						//imprimir verde
					}
				 }
				else{
					board +=("<td><button class="+'"'+"cfondo"+'"'+"></button>");
					board +=("</td>");
					// imprirmr blanco
				}
			}
			board +=("</tr>");
		}
		board +=("</table>");
		board +=("<table align="+'"'+"center"+'"'+"class="+'"'+"trapecio"+'"'+"></table>");
		return board;
	}


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
				if(ii<column){
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
		}
		/*this verify the diagonal right to left if some player wins*/
		for(int i=column; i>0; i--){
			count = 0;
			int ii = i;
			for (int j=0 ;j<row; j++){
				if(ii>0){
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
		}
		return 0 ;
	}

	public boolean end(Grid state){
		if(state.fullBoard())
			return true;
		if(state.searchWinner(1) == 1)
			return true;
		if(state.searchWinner(2) == 2)
			return true;
		return false;
	}

/* this method clone an state, but with out her references,this is uses when overlap the data*/
	public Grid clone() {
    if (this == null) 
      return null;
    Grid aux = new Grid(6,7);
    // if(this.flag)
    // 	aux.flag = true;
    // else
    // 	aux.flag = false;
    for (int i = 0; i<=row; i++) {
    	for (int j = 0; j<=column; j++) {
    		if(this.grid[i][j]!= null)
    			aux.grid[i][j] = new Cell (this.grid[i][j].getCell());
    	}
    }
    return aux;
  }

	public LinkedList<Grid> getSuccessors(Grid state) {
		LinkedList<Grid> list = new LinkedList<Grid>();
		int player;	
		if(state.isMax())
			player = 2;
		else
			player=1;

		for (int j = 0; j<=column; j++){
   			Grid aux = state.clone();
   			aux.level = state.level *-1;
			if(!aux.fullColumn(j+1)){
				aux.putACell(player,j+1);
				list.add(aux);
			}
		} 
	
		return list;
	}
	

}
	
public class Game{

	Token[][] grid;
	private int file;
	private int column;
	private int tokens;

	/*CONSTRUCTOR*/
	/*this method build the structure of an board, and sets the atributs of this method*/
	public Grid(int aFile, int aColumn){
		grid = new Token[aFile][aColumn];
		file = aFile-1;
		column = aColumn-1;
		tokens = 0;

		for(int i=0; i<=file; i++)
			for (int j=0 ;j<=column; j++)
				grid[i][j]=null;
	}



/*this method search a winner of this game*/
	public int searchWinner(){
		Integer playerOne = 1;
		Integer playerTwo = 2;
		int count = 0;

 /*verify if the player number one is winner or dont*/
		for(int i=0; i<=file; i++){
			count = 0;
			for (int j=0 ;j<column; j++){
				System.out.println(count+" player 1");
				if(count == 4)
					return 1;
				if(grid[i][j]==null||grid[i][j+1] ==null)
					count =0;
				else{
				//	System.out.println(playerOne.compareTo(grid[i][j].getToken()));
				//	System.out.println(playerOne.compareTo(grid[i][j+1].getToken()));
					if(playerOne.compareTo(grid[i][j].getToken()) == 0 && 0 ==playerOne.compareTo(grid[i][j+1].getToken()))
						count++;
					else
						count = 0;
				}
			}	
		}

			for(int i=0; i<=column; i++){
			count = 0;
			for (int j=0 ;j<file; j++){
				System.out.println(count+" player 1");
				if(count == 4)
					return 1;
				if(grid[j][i]==null||grid[j+1][i] ==null)
					count =0;
				else{
<<<<<<< HEAD:src/main/java/com/unrc/app/Game.java
					//System.out.println(playerOne.compareTo(grid[j][i].getToken()));
				//	System.out.println(playerOne.compareTo(grid[j+1][i].getToken()));
					if(playerOne.compareTo(grid[j][i].getToken())  == 0 && 0 == playerOne.compareTo(grid[j+1][i].getToken()))
=======
					System.out.println(playerOne+"="+grid[j][i].getToken());
					//System.out.println(playerOne+"="+grid[j][i+1].getToken());
					if(grid[j][i].getToken() == playerOne && playerOne == grid[j][i+1].getToken())//**CAMBIAR**
						count++;
					else
						count = 0;
				}
			}	
		}

		for(int i=0; i<=file; i++){
			count = 0;
			for (int j=0 ;j<column; j++){
				if(count == 4)
					return 1;
				if(grid[i][j]==null)
					count =0;
				else{
					if(grid[i][j].getToken() == playerOne && playerOne == grid[i+1][j+1].getToken())
>>>>>>> edcf83c8a46a9c3df25367192e054176acde3332:Grid.java
						count++;
					else
						count = 0;
				}
			}	
		}

		/*verify if the player number two is the winner or dont*/

			for(int i=0; i<=file; i++){
			count = 0;
			for (int j=0 ;j<column; j++){
				System.out.println(count+" player 2");
				if(count == 4)
					return 2;
				if(grid[i][j]==null||grid[i][j+1] ==null)
					count =0;
				else{
					//System.out.println(playerTwo.compareTo(grid[i][j].getToken()));
					//System.out.println(playerTwo.compareTo(grid[i][j+1].getToken()));
					if(playerTwo.compareTo(grid[i][j].getToken())  == 0 && 0 == playerTwo.compareTo(grid[i][j+1].getToken()))
						count++;
					else
						count = 0;
				}
			}	
		}

			for(int i=0; i<=column; i++){
				count = 0;
				for (int j=0 ;j<file; j++){
					if(count == 4)
						return 2;
					if(grid[j][i]==null)
						count =0;
					else{
						if(grid[j][i].getToken() == playertwo && playertwo == grid[j][i+1].getToken())//**CAMBIAR**
							count++;
						else
							count = 0;
					}	
			}	
		}

		for(int i=0; i<=file; i++){
			count = 0;
<<<<<<< HEAD:src/main/java/com/unrc/app/Game.java
			for (int j=0 ;j<file; j++){
				System.out.println(count+" player 2");
				if(count == 4)
					return 2;
				if(grid[j][i]== null||grid[j+1][i] ==null)
					count =0;
				else{
				//	System.out.println(playerTwo.compareTo(grid[j][i].getToken()));
				//	System.out.println(playerTwo.compareTo(grid[j+1][i].getToken()));
					if(playerTwo.compareTo(grid[j][i].getToken())  == 0 && 0 == playerTwo.compareTo(grid[j+1][i].getToken()))
=======
			for (int j=0 ;j<column; j++){
				if(count == 4)
					return 2;
				if(grid[i][j]==null)
					count =0;
				else{
					if(grid[i][j].getToken() == playertwo && playertwo == grid[i+1][j+1].getToken())
>>>>>>> edcf83c8a46a9c3df25367192e054176acde3332:Grid.java
						count++;
					else
						count = 0;
				}
			}	
		}

		return 0; //if no body wins, this returns 0, that is equal to said, for doubt no body wins
		//return 1 ;//= ganadaron el jugador nº 1
		// return 2 = ganadaron el jugador nº 2
		// return 0 = nadie gano
	}
	/*this return true if the board is complete*/
	public Boolean fullBoard(){
		return tokens == (file+1)*(column+1);
	}
	 /*this return true if te column where ther player wish to insert is  full*/
	public Boolean fullColumn(int aColumn){
		return grid[0][aColumn-1] != null;	
	}


	/*this method modeling an user inserting a token in the board*/
	public void play(Integer player,int aColumn){
		if (!fullColumn(aColumn))
			putAToken(player,aColumn);
		int game = searchWinner();
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
					System.out.print(" "+grid[i][j].getToken());
				else
					System.out.print(" 0");
				}	
			System.out.println();
		}
	}

	/*this method modeling how we insert a toke, we choose de dicotomic insert,
	cause its more efficient that lineal form*/
	public void putAToken(Integer player,int aColumn){
		Token token = new Token(player);
		aColumn--;
		int limDown = file;
		int limUp = 0;
		int half;
		Boolean cond = true;
		tokens++;
		while(cond){
			half = (limDown+limUp)/2;

			if(grid[file][aColumn] == null){
				grid[file][aColumn] =token;
				cond = false;
			}

			if(grid[1][aColumn] != null){
				grid[0] [aColumn]=token;
				cond = false;
			}
			
			if(grid[half][aColumn]!= null)
				limDown = half;
			else{
				if(grid[half+1][aColumn] == null)
					limUp = half;
				else{
					grid[half][aColumn]= token; 
					cond = false;
				}
			}
		}
	}
	
	/*quick test*/

	public static void main(String[] args) {
		Grid grid = new Grid(7,6);
<<<<<<< HEAD:src/main/java/com/unrc/app/Game.java
		// grid.play(new Integer(1),1);
		// grid.play(new Integer(1),1);
		 grid.play(new Integer(2),1);
		 grid.play(new Integer(2),1);
		grid.play(new Integer(1),1);
		grid.play(new Integer(1),1);
		grid.play(new Integer(1),1);
		grid.play(new Integer(1),1);
		//  
		// System.out.println(grid.fullColumn(1));
=======
		//grid.play(new Integer(1),1);
		grid.putAToken(new Integer(1),1);
		grid.putAToken(new Integer(1),1);
		grid.putAToken(new Integer(1),1);
		grid.putAToken(new Integer(1),1);
		/*grid.putAToken(new Integer(2),3);
		grid.putAToken(new Integer(1),4);*/
		System.out.println(grid.fullColumn(1));
>>>>>>> edcf83c8a46a9c3df25367192e054176acde3332:Grid.java
		grid.show();
		System.out.println(grid.searchWinner());
	}
}



public class Grid{

	Token[][] grid;
	private int file;
	private int column;
	private int tokens;

	/*CONSTRUCTOR*/
	public Grid(int aFile, int aColumn){
		grid = new Token[aFile][aColumn];
		file = aFile-1;
		column = aColumn-1;
		tokens = 0;

		for(int i=0; i<=file; i++)
			for (int j=0 ;j<=column; j++)
				grid[i][j]=null;
	}

	public int searchWinner(){
		Integer playerOne = 1;
		Integer playertwo = 2;
		int count = 0;

		for(int i=0; i<=file; i++){
			count = 0;
			for (int j=0 ;j<column; j++){
				if(count == 4)
					return 1;
				if(grid[i][j].getToken() == playerOne && playerOne == grid[i][j+1].getToken())
					count++;
				else
					count = 0;
			}	
		}

			for(int i=0; i<column; i++){
			count = 0;
			for (int j=0 ;j<=file; j++){
				if(count == 4)
					return 1;
				if(grid[j][i].getToken() == playerOne && playerOne == grid[j][i+1].getToken())
					count++;
				else
					count = 0;
			}	
		}









			for(int i=0; i<=file; i++){
			count = 0;
			for (int j=0 ;j<column; j++){
				if(count == 4)
					return 2;
				if(grid[i][j].getToken() == playertwo && playertwo == grid[i][j+1].getToken())
					count++;
				else
					count = 0;
			}	
		}

			for(int i=0; i<=column; i++){
			count = 0;
			for (int j=0 ;j<file; j++){
				if(count == 4)
					return 2;
				if(grid[j][i].getToken() == playertwo && playertwo == grid[j][i+1].getToken())
					count++;
				else
					count = 0;
			}	
		}

		return 0; 








		 //return 1 ;//= ganadaron el jugador nº 1
		// return 2 = ganadaron el jugador nº 2
		// return 0 = es un empate 
		// return -1 = nadie gano
		// 
	}

	public Boolean fullBoard(){
		return tokens == (file+1)*(column+1);
	}

	public Boolean fullColumn(int aColumn){
		return grid[0][aColumn-1] != null;	
	}

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


	public void show(){
		for(int i=0; i<=file; i++){
			for (int j=0 ;j<=column; j++){
				System.out.print(" "+grid[i][j]);
				}	
			System.out.println();
		}
	}


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
	


	public static void main(String[] args) {
		Grid grid = new Grid(7,6);
		grid.play(new Integer(1),1);
		grid.putAToken(new Integer(2),1);
		grid.putAToken(new Integer(1),1);
		grid.putAToken(new Integer(2),1);
		grid.putAToken(new Integer(1),2);
		grid.putAToken(new Integer(2),3);
		grid.putAToken(new Integer(1),4);
		System.out.println(grid.fullColumn(1));
		grid.show();
	}
}
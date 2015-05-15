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
<<<<<<< HEAD

		for(int i=0; i<=file; i++)
			for (int j=0 ;j<=column; j++)
				grid[i][j]=null;
=======
		file = aFile;
		column = aColumn;
		grid = new Token[aFile][aColumn];

		for(int i=0; i<aFile; i++)
			for (int j=0; j<aColumn; j++)
				grid[i][j]=null;	
	}

	/*checkea si la columna en la que quiero insertar una ficha esta llena*/
	public boolean fullColumn(int thisColumn){
		boolean flag=true;
		int half=file/2;
			while (half<file){//busca en la columna de manera dicotomica
				if ((grid[thisColumn][half]).getToken()==null)
					return false;
				half=half+(file-half)/2;
			}
		return flag;
>>>>>>> 7a7052c973cc142aa8ac93b0c0b60d0bd51e3b1d
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

<<<<<<< HEAD
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
=======
	/*coloca una ficha en alguna posicion del tablero*/
// 	public void putAToken(Integer player, int aColumn){
// 		Token token = new Token(player);
// 		int limDown = 0;
// 		int limUp = file;
// 		Boolean cond = true;
// 		int half = (limUp+limDown)/2;

// 		while(cond&& limUp>limDown){
// 			half = (limUp+limDown)/2;
// 			System.out.println((half-1)+" "+ (half+1));
// 			if(grid[0][column]==null){
// 				half =0;
// 				cond=false;
// 			}
// else{
// 			if(grid[half-1][column] == null )
// 				limUp=half;
// 			else{
// 				if (grid[half+1][column] == null)
// 					limDown=half;
// 				else{
					
// 					cond = false;
// 				}
// 			}
// 		}
// 			}
// 			grid[half][column] = token;
// 	}


/*

arreglo[x][y]

				 y y y y y y
				____________
			x	|1 2 3 4 5 6
			x	|1 2 3 4 5 6
			x	|1 2 3 4 5 6
			x	|1 2 3 4 5 6 
			x	|1 2 3 4 5 6 
			x	|1 2 3 4 5 6 
			x	|1 2 3 4 5 6 


*/
	public void putAToken(Integer player, int aColumn){
		int limUp = file-1;
		int limDown = 0;
		int half = (limUp + limDown)/2;
		Token aToken = new Token(player);
		while(limUp > limDown + 1){
			System.out.println("half" + half);
			if (half == 0){
				grid[half-1][aColumn] = aToken;
				System.out.println("half" + half);
			}
			else{
				half = (limUp + limDown)/2;
				if (grid[half][aColumn] == null){
					if (grid[half-1][aColumn] != null){
						System.out.println("half" + half);
						grid[half][aColumn] = aToken;
					}
					else{
						limUp  = half;
					}
				}
				else{
					if (grid[half+1][aColumn] == null){
						System.out.println("half" + half);
						grid[half+1][aColumn] = aToken;
					}
					else{
						limDown = half;
					}
			// System.out.println("half" + half);
			// System.out.println("limUp" + limUp);
			// System.out.println("limDown" + limDown);
				}
			}
		}
		for (int i = 0; i < file ; i++) {
			for (int j = 0; j < column ; j++) {
				System.out.print(grid[i][j]+" ");
			}		
			System.out.println();	
>>>>>>> 7a7052c973cc142aa8ac93b0c0b60d0bd51e3b1d
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
<<<<<<< HEAD
		grid.play(new Integer(1),1);
		grid.putAToken(new Integer(2),1);
		grid.putAToken(new Integer(1),1);
		grid.putAToken(new Integer(2),1);
		grid.putAToken(new Integer(1),2);
		grid.putAToken(new Integer(2),3);
		grid.putAToken(new Integer(1),4);
		System.out.println(grid.fullColumn(1));
		grid.show();
=======
		int cantidad=0;

		// System.out.println(grid.putAToken(2,1));
		//grid.putAToken(new Integer(2),1);
		grid.putAToken(new Integer(1),0);
>>>>>>> 7a7052c973cc142aa8ac93b0c0b60d0bd51e3b1d
	}
}
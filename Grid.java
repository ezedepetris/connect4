public class Grid{

	private Token[][] grid;
	private int file=6;
	private int column=7;

	/*CONSTRUCTOR*/
	public Grid(int file, int column){
		Grid[][] tablero = new Grid[column][file];
		for(int i=0; i<column; i++)
			for (int j=0; j<file; j++){
				Token token = new Token();
				grid[i][j]=token;	
			}	
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
	}

	/*indica si el tablero esta completo o no*/
	public boolean fullGrid(int cantOfToken){
		int totalOfToken=file*column;
		return totalOfToken==cantOfToken;
	}

	/*coloca una ficha en alguna posicion del tablero*/
	public void putAToken(Integer player, int thisColumn){
		Token token = new Token(player);
		int half=thisColumn/2;
			while (half<thisColumn){//busca en la columna de manera dicotomica
				if ((grid[thisColumn][half]).getToken()==null){
					grid[thisColumn][half]=token;
					half=thisColumn;
				}
				else
					half=half+(thisColumn-half)/2;
			}
	}

	/*despues de colocar una ficha dice si ese jugador es el ganador*/
	public boolean searchWiner(int x, int y){
		boolean flag1=true;
		boolean flag2=true;
		for(int i=x; i>x-3; i--){
			if (((grid[i][y]).getToken())!=((grid[i-1][y]).getToken()))
				flag1 = false;
		}
		for(int j=y; j>y-3; j--){
			if (((grid[x][j]).getToken())!=((grid[x][j-1]).getToken()))
				flag2 = false;
		}
		return (flag1||flag2);
	}

	public Integer playAGame(Grid grid, int cantOfToken, int thisColumn, Integer player){
		if (grid.fullGrid(cantOfToken))
			return 0;
		else{
			if (grid.fullColumn(thisColumn))
				System.out.println("***ERROR***");
			else{
				grid.putAToken(player,thisColumn);
				cantOfToken++;
			}
			for(int i=column; i>0; i--)
				for(int j=file; j>0; j--){
					if (grid.searchWiner(i,j))
						return player;
				}
			return 0;
		}

	}

	public static void main(String[] args) {
		Grid grid = new Grid(7,6);
		int cantidad=0;
		System.out.println(grid.playAGame(grid,cantidad,2,1));
	}


}
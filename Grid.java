public class Grid{

	private Token[][] grid;
	private int file;
	private int column;
	private int tokens;

	/*CONSTRUCTOR*/
	public Grid(int aFile, int aColumn){
		tokens = 0;
		file = aFile;
		column = aColumn;
		grid = new Token[aColumn][aFile];

		for(int i=0; i<aColumn; i++)
			for (int j=0; j<aFile; j++)
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
	}

	/*indica si el tablero esta completo o no*/
	public boolean fullGrid(){
		
		return tokens == file*column;
	}

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
		Integer[][] hola = new Integer[file][column];
		hola[6][3] = 1;
		for (int i=0;i<file ;i++ ) {
			for (int j =0;j<column ;j++ ) {
				System.out.print(hola[i][j]+" ");
			}		
			System.out.println();	
		}
		int limUp = file-1;
		int limDown = 0;
		int half;
		Token aToken = new Token(player);
		while(limUp>limDown){
			half=(limUp+limDown)/2;
			if (grid[aColumn][half]==null)
				if (grid[aColumn][half-1]!=null)
					grid[aColumn][half]= aToken;
				else
					limUp=half;
				else
					if (grid[aColumn][half+1]==null)
						grid[aColumn][half+1]= aToken;
					else
						limDown=half;
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

	public Integer playAGame( int thisColumn, Integer player){
		if (tokens == file * column)
			return 0;
		else{
			if (fullColumn(thisColumn))
				System.out.println("***ERROR***");
			else{
				putAToken(player,thisColumn);
				tokens++;
			}
			for(int i=column; i>0; i--)
				for(int j=file; j>0; j--){
					if (false)
						return player;
				}
			return 0;
		}

	}

	public static void main(String[] args) {
		Grid grid = new Grid(7,6);
		int cantidad=0;

		// System.out.println(grid.putAToken(2,1));
		grid.putAToken(new Integer(2),1);
		grid.putAToken(new Integer(1),1);
	}


}
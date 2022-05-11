import java.util.ArrayList;
import java.util.Random;
/**
 * Write a description of class gameOfLife here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class gameOfLife
{
    // instance variables - replace the example below with your own
    Random rand = new Random();
    public int Columns = 20;
    public int Rows = 20;
    
    // int[][] grid = 
    
                      // { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },	
                        // { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },	
                        // { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			// { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			// { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			// { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
			// { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
			// { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			// { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			// { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
		// };
    
		
		
		int[][] grid = 
    
                      { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },	
                        { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },	
                        { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
		};
    //public boolean ShowGeneration = false;

    public gameOfLife() {
        System.out.println('\u000c');
        
        int board[][] = new int[Rows][Columns];
        for(int x = 0; x<Columns; x++){
            for(int y = 0; y<Rows; y++){
                board[x][y] = (rand.nextInt(2))*(rand.nextInt(2));
                System.out.print(board [x] [y] + "  ");
            }
            System.out.println();
        }
        
    }
    
    // Function to print next generation
	public void nextGeneration(int grid[][], int Rows, int Columns)
	{
		int[][] future = new int[Rows][Columns];

		// Loop through every cell
		for (int l = 0; l < Rows; l++)
		{
			for (int m = 0; m < Columns; m++)
			{
				// finding no Of Neighbours that are alive
				int aliveNeighbours = 0;
				for (int i = -1; i <= 1; i++)
					for (int j = -1; j <= 1; j++)
					if ((l+i>=0 && l+i<Rows) && (m+j>=0 && m+j<Columns))
						aliveNeighbours += grid[l + i][m + j];

				// The cell needs to be subtracted from
				// its neighbours as it was counted before
				aliveNeighbours -= grid[l][m];

				// Implementing the Rules of Life

				// Cell is lonely and dies
				if ((grid[l][m] == 1) && (aliveNeighbours < 2))
					future[l][m] = 0;

				// Cell dies due to over population
				else if ((grid[l
				][m] == 1) && (aliveNeighbours > 3))
					future[l][m] = 0;

				// A new cell is born
				else if ((grid[l][m] == 0) && (aliveNeighbours == 3))
					future[l][m] = 1;

				// Remains the same
				else
					future[l][m] = grid[l][m];
			}
		}
}
}



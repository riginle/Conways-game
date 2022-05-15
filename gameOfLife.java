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
    public int Columns = 10;
    public int Rows = 10;

    // int[][] board = 

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

    int[][] board = 
        { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },    
            { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },    
            { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };
    //public boolean ShowGeneration = false;

    public gameOfLife() {
        System.out.println('\u000c');
        //int board[][] = new int[Rows][Columns];
        for(int i = 0; i<Columns; i++){
            for(int j = 0; j<Rows; j++){
                //board[x][y] = (rand.nextInt(2))*(rand.nextInt(2));
                System.out.print(board [i] [j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
        nextGeneration(board, Columns, Rows);
    }
    
    public void nextGeneration(int board[][], int Rows, int Columns)
    {
        int[][] future = new int[Rows][Columns];
        for (int l = 0; l < Rows; l++)
        {
            for (int m = 0; m < Columns; m++)
            {
                
                int aliveNeighbours = 0;
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++)
                        if ((l+i>=0 && l+i<Rows) && (m+j>=0 && m+j<Columns))
                            aliveNeighbours += board[l + i][m + j]; 
                aliveNeighbours -= board[l][m];
                // Cell is lonely and dies
                if ((board[l][m] == 1) && (aliveNeighbours < 2))
                    future[l][m] = 0;

                // Cell dies due to over population
                else if ((board[l
                    ][m] == 1) && (aliveNeighbours > 3))
                    future[l][m] = 0;
                // A new cell is born
                else if ((board[l][m] == 0) && (aliveNeighbours == 3))
                    future[l][m] = 1;
                // Remains the same
                else
                    future[l][m] = board[l][m];
            }
        }
        for(int i = 0; i<Columns; i++){
            for(int j = 0; j<Rows; j++){
                
                System.out.print(future [i] [j] + "  ");
            }
            System.out.println();
        }
    }
}


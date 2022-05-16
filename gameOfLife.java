import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
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
    public int Columns = 30;
    public int Rows = 30;

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

    // int[][] board = 
    // { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },    
    // { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },    
    // { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
    // { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
    // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    // { 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
    // { 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
    // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
    // };
    public boolean ShowGeneration = false;

    public gameOfLife() {
        System.out.println('\u000c');
        int board[][] = new int[Rows][Columns];
        for(int yModifer= 0; yModifer<Columns; yModifer++){
            for(int j = 0; j<Rows; j++){
                board[yModifer][j] = (rand.nextInt(2))*(rand.nextInt(2));
                System.out.print(board [yModifer] [j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
        nextGeneration(board, Columns, Rows);
    }

    public void nextGeneration(int board[][], int Rows, int Columns)
    {
        for (int Gen = 0; Gen < 100; Gen++)
        {
                try {
                        TimeUnit.MILLISECONDS.sleep(300);
                    }
                    catch (Exception e) {

                    }
                            System.out.println('\u000c');

            int[][] future = new int[Rows][Columns];

            for (int y= 0; y< Rows; y++)
            {
                for (int x= 0; x< Columns; x++)
                {

                    int aliveNeighbours = 0;
                    for (int yModifer= -1; yModifer<= 1; yModifer++)
                        for (int j = -1; j <= 1; j++)
                            if ((y+yModifer>=0 && y+yModifer<Rows) && (x+j>=0 && x+j<Columns))
                                aliveNeighbours += board[y+ yModifer][x+ j]; 
                    aliveNeighbours -= board[y][x];
                    // Cell is lonely and dies
                    if ((board[y][x] == 1) && (aliveNeighbours < 2))
                        future[y][x] = 0;

                    // Cell dies due to over population
                    else if ((board[y][x] == 1) && (aliveNeighbours > 3))
                        future[y][x] = 0;
                    // A new cell is born
                    else if ((board[y][x] == 0) && (aliveNeighbours == 3))
                        future[y][x] = 1;
                    // Remains the same
                    else
                        future[y][x] = board[y][x];
                }
            }

            
            for(int yModifer = 0; yModifer<Columns; yModifer++){
                for(int j = 0; j<Rows; j++){
                    
                    
                    System.out.print(future [yModifer] [j] + "  ");
                }
                System.out.println();
            }

            
            System.out.println();
            board = future;
            //future = board;

        }
    }
}


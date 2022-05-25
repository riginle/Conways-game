import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
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
    //public int Columns = 10;
    //public int Rows = 10;
    public String Alive = "X";
    public String Death = " ";

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
    public boolean selectionScreen = false;

    public gameOfLife() {
        System.out.println('\u000c');

        System.out.println("How many rows and columns do you want? only input one number.");
        Scanner keyboardIn = new Scanner(System.in);

        int Columns = keyboardIn.nextInt();
        keyboardIn.nextLine();
        int Rows = Columns;

        System.out.println("This is the board");
        // int Rows = keyboardIn.nextInt();
        // keyboardIn.nextLine();

        //int GenInput = keyboardIn.nextInt();
        //  keyboardIn.nextLine();

        int board[][] = new int[Rows][Columns];
        for(int yModifer= 0; yModifer<Columns; yModifer++){
            for(int xModifer = 0; xModifer<Rows; xModifer++){
                board[yModifer][xModifer] = (rand.nextInt(2))*(rand.nextInt(2));
                System.out.print(board [yModifer] [xModifer] + "  ");
            }
            System.out.println();
        }
        System.out.println();

        // System.out.println("do you want to enter selction screen?");

        // String keyInput = keyboardIn.nextLine().toLowerCase();

        // if (keyInput.equalsIgnoreCase("start select")){
            // selectionScreen = true;
            // System.out.println("do you wantrtgrrzrhrhdzrhzhrhz to enter selction screen?");
        // }

        // while (selectionScreen == true){

            // System.out.println("Please select row: ");
            // int rowSelection = keyboardIn.nextInt();
            // System.out.println("Please select column: ");
            // int columnSelection = keyboardIn.nextInt();

           
            
            // if (board[rowSelection][columnSelection] == 0){
                // board[rowSelection][columnSelection] = 1;
            // }
            // else
            // {
                // board[rowSelection][columnSelection] = 0;

            // }
            // for(int yModifer= 0; yModifer<Columns; yModifer++){
                // for(int xModifer = 0; xModifer<Rows; xModifer++){
                    // System.out.print(board [yModifer] [xModifer] + "  ");
                // }
                // System.out.println();
            // }
            // System.out.println();
            
            // selectionScreen=false; 
        // }
        
        // System.out.println("do you want to continuw selction screen?");
        
        // keyboardIn.nextLine().toLowerCase();
        
        // if (keyInput.equalsIgnoreCase("start select")){
            // selectionScreen = true;
            // System.out.println("do you wantrtgrrzrhrhdzrhzhrhz to enter selction screen?");
        // }
        
        nextGeneration(board, Columns, Rows);
    }

    public void nextGeneration(int board[][], int Rows, int Columns)
    {

        int countDead = 0;

        Scanner keyboardIn = new Scanner(System.in);

        System.out.println("How many Generations would you like?");
        int genInput = keyboardIn.nextInt();
        keyboardIn.nextLine();

        int numberFix = 20;

        // String Width = keyboardIn.nextLine().replaceAll("\\D", "");
        // if(Width != "") //If statement checks Width string is null
        // {
        // numberFix = Integer.parseInt(Width); //Replaces all non-numbers with nothing
        // System.out.println("WIDTH set to "+numberFix+"\n");
        // } else
        // {
        // System.out.println("ass");
        // }

        System.out.println("How many miliseconds between generations would you like?");

        int timerInput = keyboardIn.nextInt();
        keyboardIn.nextLine();

        System.out.println("What would you like to represent your alive cells?");

        String aliveInput = keyboardIn.nextLine();

        for (int Gen = 0; Gen < genInput; Gen++)
        {
            try {
                TimeUnit.MILLISECONDS.sleep(timerInput);
            }
            catch (Exception e) {

            }
            System.out.println('\u000c');
            System.out.println("There are " + Gen + " Generations");
            int[][] future = new int[Rows][Columns];

            for (int y= 0; y< Rows; y++)
            {
                for (int x= 0; x< Columns; x++)
                {

                    int aliveNeighbours = 0;
                    for (int yModifer= -1; yModifer<= 1; yModifer++)
                        for (int xModifer = -1; xModifer <= 1; xModifer++)
                            if ((y+yModifer>=0 && y+yModifer<Rows) && (x+xModifer>=0 && x+xModifer<Columns))
                                aliveNeighbours += board[y+ yModifer][x+ xModifer];
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
                for(int xModifer = 0; xModifer<Rows; xModifer++){

                    if(future[yModifer][xModifer] == 1){
                        System.out.print(""+ aliveInput +" ");
                    }
                    else{
                        System.out.print("  ");
                    }

                    //System.out.print(future [yModifer] [xModifer] + "  ");
                }
                System.out.println();
            }

            //for (int deadModifer = 0; deadModifer < 1; deadModifer++){
            // System.out.println("AHHggggggHHHHHHH");
            // if (board == future) {
            // countDead++;
            // System.out.println("AHHHHHHHHHHHH");
            // }
            // // }
            //System.out.println("" + countDead + "");

            if (Arrays.deepEquals(board, future)){

                System.out.println("Simulation over");
                return;
            }

            // int deadBoard = 0;
            // for(int yModifer = 0; yModifer<Columns; yModifer++){
            // for(int xModifer = 0; xModifer<Rows; xModifer++){

            // deadBoard += future[yModifer][xModifer];

            // //System.out.print(future [yModifer] [xModifer] + "  ");
            // }
            // if(deadBoard == 0){
            // System.out.println("Fransoaw");
            // return;

            // }
            // }

            System.out.println();
            board = future;
            //future = board;
        }
    }

    public void printFuture(){

    }
}


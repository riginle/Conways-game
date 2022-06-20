//14/06/2022


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

    String Alive = "X";
    String Death = " ";

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
    boolean selectionScreen = false;
    boolean menuScreen = true;
    int timerInput;
    int[][] future;
    int board[][];
    int aliveInput;
    int countDead;
    int genInput;
    int numberFix;
    public gameOfLife() {
        System.out.println('\u000c');

        slowPrint("How many rows and columns do you want? only input one number.");
        Scanner keyboardIn = new Scanner(System.in);

        int Columns = keyboardIn.nextInt();
        keyboardIn.nextLine();
        int Rows = Columns;

        slowPrint("This is the board");
        System.out.println();
        board = new int[Rows][Columns];
        printBoard( board, Rows, Columns);

        while (menuScreen == true){
            slowPrint("Do you want to enter selection screen? type yes if, yes. no if, no. if you want to quit the program, quit");

            String keyInput = keyboardIn.nextLine().toLowerCase();

            if (keyInput.equalsIgnoreCase("yes")){
                selectionScreen = true;
            }

            else if(keyInput.equalsIgnoreCase("no")){
                nextGeneration(board, Columns, Rows);
            }

            else if (keyInput.equalsIgnoreCase("quit")){
                menuScreen = false;
                return;
            }

            else {
                slowPrint("Invalid command");
            }
            System.out.println();

            while (selectionScreen == true){

                slowPrint("Please select row: ");
                int rowSelection = keyboardIn.nextInt();
                slowPrint("Please select column: ");
                int columnSelection = keyboardIn.nextInt();

                if (board[rowSelection-1][columnSelection-1] == 0){
                    board[rowSelection-1][columnSelection-1] = 1;
                }
                else
                {
                    board[rowSelection-1][columnSelection-1] = 0;

                }
                for(int yModifer= 0; yModifer<Columns; yModifer++){
                    for(int xModifer = 0; xModifer<Rows; xModifer++){
                        System.out.print(board [yModifer] [xModifer] + "  ");
                    }
                    System.out.println();
                }
                System.out.println();

                selectionScreen=false;
            }

        }            
        nextGeneration(board, Columns, Rows);
    }

   
   
   
    public void nextGeneration(int board[][], int Rows, int Columns)
    {

        countDead = 0;

        Scanner keyboardIn = new Scanner(System.in);

        slowPrint("How many Generations would you like?");

        genInput = keyboardIn.nextInt();

        keyboardIn.nextLine();

        numberFix = 20;

        slowPrint("How many miliseconds between generations would you like?");

        timerInput = keyboardIn.nextInt();

        keyboardIn.nextLine();

        slowPrint("What would you like to represent your alive cells?");

        String aliveInput = keyboardIn.nextLine();

        for (int Gen = 0; Gen < genInput; Gen++)
        {

            timeWaitBetweenGens();

            System.out.println('\u000c');
            System.out.println("There are " + Gen + " Generations");

            conwaysRules(board, Rows,  Columns);

            printAlive(board, Rows,  Columns);

            if (Arrays.deepEquals(board, future)){
                slowPrint("Simulation over");
                return;

            }
            board = future;

        }
    }

    void timeWaitBetweenGens(){
        try {
            TimeUnit.MILLISECONDS.sleep(timerInput);
        }
        catch (Exception e) {

        }
    }

    void conwaysRules(int board[][], int Rows, int Columns){
        future = new int[Rows][Columns];

        for (int y= 0; y< Rows; y++)
        {
            for (int x= 0; x< Columns; x++)
            {

                int aliveNeighbours = 0;
                for (int yModifer= -1; yModifer<= 1; yModifer++)
                    for (int xModifer = -1; xModifer <= 1; xModifer++)
                        if ((y+yModifer>=0 && y+yModifer<Rows) && (x+xModifer>=0 && x+xModifer<Columns)){

                            aliveNeighbours += board[y+ yModifer][x+ xModifer];
                        }
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
    }

    void printAlive(int board[][], int Rows, int Columns){
        for(int yModifer = 0; yModifer<Columns; yModifer++){
            for(int xModifer = 0; xModifer<Rows; xModifer++){

                if(future[yModifer][xModifer] == 1){
                    System.out.print(""+ aliveInput +" ");
                }
                else{
                    System.out.print("  ");
                }

            }
            System.out.println();
        }
        System.out.println();
    }

    public static void slowPrint(String output) {
        // this is for making text print out slower
        // it was taken from the internet i do not understand how it works (it just looks nice)        
        for (int i = 0; i<output.length(); i++) {
            char c = output.charAt(i);
            System.out.print(c);
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            }
            catch (Exception e) {
            }
        }
    }

    void printBoard (int board[][], int Rows, int Columns){
        //board = new int[Rows][Columns];
        for(int yModifer= 0; yModifer<Columns; yModifer++){
            for(int xModifer = 0; xModifer<Rows; xModifer++){
                board[yModifer][xModifer] = (rand.nextInt(2))*(rand.nextInt(2));
                slowPrint(board [yModifer] [xModifer] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
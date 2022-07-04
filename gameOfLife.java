
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.lang.Object;
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
    String aliveInput;
    int countDead;
    int genInput;
    int numberFix;
    int size = 5;
    String keyInput;
    int Columns;
    int Rows;
    Scanner keyboardIn ;
    boolean seedSelection = true; 

    public gameOfLife() {
        System.out.println('\u000c');
        board = new int[Rows][Columns];
        slowPrint("Would you like to use a seed?");
        keyboardIn = new Scanner(System.in);
        
        while (seedSelection == true){
            keyInput = keyboardIn.nextLine().toLowerCase();
            if (keyInput.equalsIgnoreCase("yes")){
                slowPrint("How many rows and columns do you want? only input one number. if it is smaller dimensions than the seed it will not work.");

                Scanner keyboardIn = new Scanner(System.in);
                System.out.println();
                Columns = removeChar(keyboardIn.nextLine());
                Rows = Columns;
                slowPrint("This is the board");
                System.out.println();
                board = new int[Rows][Columns];
                trySeedFile();
                printBoard( board, Rows, Columns);
                seedSelection = false;
            }

            else if(keyInput.equalsIgnoreCase("no")){
                slowPrint("How many rows and columns do you want? only input one number.");

                Scanner keyboardIn = new Scanner(System.in);
                System.out.println();
                Columns = removeChar(keyboardIn.nextLine());
                Rows = Columns;
                slowPrint("This is the board");
                System.out.println();
                board = new int[Rows][Columns];
                printRandBoard( board, Rows, Columns);
                seedSelection = false;
            }

            else {
                slowPrint("Invalid command");
                
            }
        }

        while (menuScreen == true){

            slowPrint("Do you want to enter selection screen? type yes if, yes. no if, no. if you want to quit the program, quit");
            keyInput = keyboardIn.nextLine().toLowerCase();
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
                int rowSelection = removeChar(keyboardIn.nextLine());
                slowPrint("Please select column: ");
                int columnSelection = removeChar(keyboardIn.nextLine());
                keyboardIn.nextLine();

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

        genInput = removeChar(keyboardIn.nextLine());

        numberFix = 20;
        slowPrint("What would you like to represent your alive cells?");
        aliveInput = keyboardIn.nextLine();
        slowPrint("How many miliseconds between generations would you like?");
        timerInput = removeChar(keyboardIn.nextLine());

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
    //for the seed print 
    void printBoard (int board[][], int Rows, int Columns){
        //board = new int[Rows][Columns];
        for(int yModifer= 0; yModifer<Columns; yModifer++){
            for(int xModifer = 0; xModifer<Rows; xModifer++){
                //board[yModifer][xModifer] = (rand.nextInt(2))*(rand.nextInt(2));
                slowPrint(board [yModifer] [xModifer] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    void printRandBoard (int board[][], int Rows, int Columns){
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

    void trySeedFile(){
        //defines file
        File seed = new File("ConwaysFile.txt");

        try{
            //creates scanner
            Scanner fileReader = new Scanner(seed);
            //code that takes the 20x20 grid in character by character and places into array
            for (int seedY = 0; seedY<size; seedY++){
                //reads next line
                String fileString  = fileReader.nextLine();
                for (int seedX = 0; seedX<size; seedX++){
                    //board[seedY][seedX] = Integer.parseInt(""+fileString.charAt(seedX));

                    //takes the unicode character for "0" and subtracts the numerical value of the cell selected. if subtracted from itself, it gives 0, and if subtracted from "1", gives 1.
                    board[seedY][seedX]=fileString.charAt(seedX)-'0';
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    int removeChar(String stringInput){
        //removes all values that aren't between 0-9
        String numberOnly = stringInput.replaceAll("[^0-9]","");
        if(numberOnly.equals("")){
            //if value is invalid/lesser than 0, returns safe number of 20
            System.out.println("Invalid input. setting to default of 20.");
            return 20;
        }
        int numberValue = Integer.parseInt(numberOnly);
        if(numberValue <= 0){
            //if value is invalid/lesser than 0, returns safe number of 20
            System.out.println("Invalid input. setting to default of 20.");
            return 20;
        }else{
            //returns value without characters
            return numberValue;
        }
    }
}

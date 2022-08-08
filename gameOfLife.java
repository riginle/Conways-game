
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

    //varibles below 
    boolean selectionScreen = false;
    boolean menuScreen = true;
    int timerInput;
    //future is the array which has gone through the conways rules
    int[][] future; 
    //board is the array which has not gone through conways rules 
    int board[][];
    String aliveInput;
    int countDead;
    int genInput;
    int defultNumber;
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
            
            //if yes is slected the board will be filled by the config of the text file by calling to the trySeedFile method, 
            //before doing that the user is given the option of how big the board is.
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
            //if no is input the user will be asked the grid size and then it will print the board and while doing so fill it randonmly with dead or alive cells by calling to printRandBoard
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
            //this is in case neither yes or no is input, and will tell the player it is not an option, then they can try again to input somthing valid 
            else {
                slowPrint("Invalid command, please input yes or no");
            }
        }

        while (menuScreen == true){

            slowPrint("Do you want to enter selection screen? type yes if, yes. no if, no. if you want to quit the program, quit");
            keyInput = keyboardIn.nextLine().toLowerCase();
            //if slected brings player to the selectionScreen 
            if (keyInput.equalsIgnoreCase("yes")){
                selectionScreen = true;
            }
            //if slected moves on to other inputs
            else if(keyInput.equalsIgnoreCase("no")){
                nextGeneration(board, Columns, Rows);
            }
            //if slected closes the game
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

            conwaysRules(board, Rows, Columns);

            printAlive(board, Rows, Columns);

            if (Arrays.deepEquals(board, future)){
                slowPrint("Simulation over");
                return;

            }
            
            // this line takes the information from the prevoius future array and puts into the board array. 
            board = future;

        }
    }
    // this method is so I can cause a time delay between generations, this delay is set to a varible "timerInput" which the user can set to a value they want 
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
                // alive neigbhours is cells which are alive near a selected cell 
                for (int yModifer= -1; yModifer<= 1; yModifer++)
                    for (int xModifer = -1; xModifer <= 1; xModifer++)
                        if ((y+yModifer>=0 && y+yModifer<Rows) && (x+xModifer>=0 && x+xModifer<Columns)){

                            aliveNeighbours += board[y+ yModifer][x+ xModifer];
                        }
                aliveNeighbours -= board[y][x];

                //there are 4 rules to conways game of life
                //Any live cell with fewer than two live neighbours dies, as if by underpopulation.
                //Any live cell with two or three live neighbours lives on to the next generation.
                //Any live cell with more than three live neighbours dies, as if by overpopulation.
                //Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

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
    //this method uses a nested loop to go through future board then looks if a cell is a 1, if so it will print with the string varible in place of the 1, if a 0 it will leave the space empty
    //it prints out the alive cells and leaves the dead cells empty 
    void printAlive(int board[][], int Rows, int Columns){
        for(int yModifer = 0; yModifer<Columns; yModifer++){
            for(int xModifer = 0; xModifer<Rows; xModifer++){

                if(future[yModifer][xModifer] == 1){
                    System.out.print(aliveInput +" ");
                }
                else{
                    System.out.print("  ");
                }

            }
            System.out.println();
        }
        System.out.println();
    }
    //used to make the text printing look nicer
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
    //goes through board and prints it
    void printBoard (int board[][], int Rows, int Columns){
        //board = new int[Rows][Columns];
        //nested loop which prints out the bored
        for(int yModifer= 0; yModifer<Columns; yModifer++){
            for(int xModifer = 0; xModifer<Rows; xModifer++){
                //board[yModifer][xModifer] = (rand.nextInt(2))*(rand.nextInt(2));
                slowPrint(board [yModifer] [xModifer] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
    //for the random print
    //nested loop which randomly fills the baorded with a 1 or 0 then prints it out
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
        defultNumber = 20;
        //removes all values that aren't between 0-9
        String numberOnly = stringInput.replaceAll("[^0-9]","");
        if(numberOnly.equals("")){
            //if value is invalid/lesser than 0, returns safe number of 20
            System.out.println("Invalid input. setting to default of" +defultNumber);
            return defultNumber;
        }
        int numberValue = Integer.parseInt(numberOnly);
        if(numberValue <= 0){
            //if value is invalid/lesser than 0, returns safe number of 20
            System.out.println("Invalid input. setting to default of" +defultNumber);
            return defultNumber;
        }else{
            //returns value without characters
            return numberValue;
        }
    }
}


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
/*
 * Write a description of class gameOfLife here.
 *
 * @author (Leonardo Riginelli)
 * @version (15/08/2022)
 */
public class gameOfLife
{
    
    Random rand = new Random();
    //testing board
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
    int columns;
    int rows;
    Scanner keyboardIn ;
    boolean seedSelection = true; 
    
    public gameOfLife() {
        
        //clears screen
        System.out.println('\u000c');
        board = new int[rows][columns];
        //explaining the game works 
        slowPrint("How it works:");
        slowPrint("This is a Version of the game The Game Of Life in which a grid full of cells \nwhich are either dead or alive will interact with each other.");
        slowPrint("\nEvery cell interacts with it the cells near it at each step in time (generation)\nThese are the possible interactions:\nNearby cells are neighbors.\nAny live cell with fewer than two live neighbours dies, by underpopulation.\nAny live cell with two or three live neighbours lives on to the next generation.\nAny live cell with more than three live neighbours dies, by overpopulation.\nAny dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.\n\nSEED: if you select the option seed the grid's population configuration \nwill be the same as that within the text file in the CONWAY'S game file. If not the population will be randomly generated.");
        slowPrint("\nWould you like to use a seed?");
        keyboardIn = new Scanner(System.in);
        
        //this code is to let you choose between two board population methods
        while (seedSelection == true){
            keyInput = keyboardIn.nextLine().toLowerCase();
            
            //if yes is selected the board will be filled by the config of the text file by calling to the trySeedFile method, 
            //before doing that the user is given the option of how big the board is.
            if (keyInput.equalsIgnoreCase("yes")){
                slowPrint("How many rows and columns do you want? only input one number. if it is smaller dimensions than the seed it will not work.");
                Scanner keyboardIn = new Scanner(System.in);
                System.out.println();
                columns = removeChar(keyboardIn.nextLine());
                rows = columns;
                slowPrint("This is the board");
                System.out.println();
                board = new int[rows][columns];
                trySeedFile();
                printBoard( board, rows, columns);
                seedSelection = false;
            }
            //if no is input the user will be asked the grid size and then it will print the board and while doing so fill it randomly with dead or alive cells by calling to printRandBoard
            else if(keyInput.equalsIgnoreCase("no")){
                slowPrint("How many rows and columns do you want? only input one number.");
                Scanner keyboardIn = new Scanner(System.in);
                System.out.println();
                columns = removeChar(keyboardIn.nextLine());
                rows = columns;
                slowPrint("This is the board");
                System.out.println();
                board = new int[rows][columns];
                printRandBoard( board, rows, columns);
                seedSelection = false;
            }
            //this is in case neither yes nor no is input, and will tell the player it is not an option, then they can try again to input somthing valid 
            else {
                slowPrint("Invalid command, please input yes or no");
            }
        }
        //this code allows the player to choose if they want to enter selection screen
        while (menuScreen == true){
            slowPrint("Selection screen will allow you to switch the state of a cell");
            slowPrint("\nDo you want to enter selection screen? type yes if, yes. no if, no. if you want to quit the program, quit");
            keyInput = keyboardIn.nextLine().toLowerCase();
            //if slected brings player to the selectionScreen 
            if (keyInput.equalsIgnoreCase("yes")){
                selectionScreen = true;
            }
            //if selected moves on to other inputs
            else if(keyInput.equalsIgnoreCase("no")){
                nextGeneration(board, columns, rows);
            }
            //if selected closes the game
            else if (keyInput.equalsIgnoreCase("quit")){
                menuScreen = false;
                return;
            }
            else {
                //
                slowPrint("Invalid command");
            }
            System.out.println();
            
            // allows the player to switch the state of a cell
            while (selectionScreen == true){
                //the playe chooses the cell by stating the row and colomn its in
                slowPrint("Please select row: ");
                int rowselection = removeChar(keyboardIn.nextLine());
                slowPrint("Please select column: ");
                int columnselection = removeChar(keyboardIn.nextLine());
                keyboardIn.nextLine();
                
                
                //siwtches sate of selected cell
                //(-1 because the grid starts at co ords 0,0 instead of usual 1,1
                if (board[rowselection-1][columnselection-1] == 0){
                    board[rowselection-1][columnselection-1] = 1;
                }
                else
                {
                    board[rowselection-1][columnselection-1] = 0;

                }
                //prints out the edited board 
                for(int yModifer= 0; yModifer<columns; yModifer++){
                    for(int xModifer = 0; xModifer<rows; xModifer++){
                        System.out.print(board [yModifer] [xModifer] + "  ");
                    }
                    System.out.println();
                }
                System.out.println();
                //turns off and allows the player to choose again
                selectionScreen=false;
            }

        }            
        nextGeneration(board, columns, rows);
    }
    //this code is for some additional inputs and funning the gen loop
    public void nextGeneration(int board[][], int rows, int columns)
    {
        Scanner keyboardIn = new Scanner(System.in);

        slowPrint("How many Generations would you like?");

        genInput = removeChar(keyboardIn.nextLine());

        numberFix = 20;
        slowPrint("What would you like to represent your alive cells?");
        aliveInput = keyboardIn.nextLine();

        slowPrint("How many miliseconds between generations would you like?");
        timerInput = removeChar(keyboardIn.nextLine());
        
        //this code goes through a set amount of times (genInput) and each time, uses the time delay,
        //prints how many gens have past and then sends the board information through the conways rules
        //then prints the future grid
        //then if the baord and future are the same later going through the conwyas rules youll be sent out of the loop
    
        for (int Gen = 0; Gen < genInput; Gen++)
        {

            timeWaitBetweenGens();

            System.out.println('\u000c');
            System.out.println("There are " + Gen + " Generations");

            conwaysRules(board, rows, columns);

            printAlive(board, rows, columns);

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
    // this code is for applying comways rules to the board
    void conwaysRules(int board[][], int rows, int columns){
        future = new int[rows][columns];

        for (int y= 0; y< rows; y++)
        {
            for (int x= 0; x< columns; x++)
            {

                int aliveNeighbours = 0;
                // alive neigbhours is cells which are alive near a selected cell 
                //adds all living cells to total
                for (int yModifer= -1; yModifer<= 1; yModifer++)
                    for (int xModifer = -1; xModifer <= 1; xModifer++)
                        if ((y+yModifer>=0 && y+yModifer<rows) && (x+xModifer>=0 && x+xModifer<columns)){

                            aliveNeighbours += board[y+ yModifer][x+ xModifer];
                        }
                //sub self form total
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
    void printAlive(int board[][], int rows, int columns){
        for(int yModifer = 0; yModifer<columns; yModifer++){
            for(int xModifer = 0; xModifer<rows; xModifer++){

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
        //
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
    void printBoard (int board[][], int rows, int columns){
        //board = new int[rows][columns];
        
        //nested loop which prints out the bored
        for(int yModifer= 0; yModifer<columns; yModifer++){
            for(int xModifer = 0; xModifer<rows; xModifer++){
                //board[yModifer][xModifer] = (rand.nextInt(2))*(rand.nextInt(2));
                
                slowPrint(board [yModifer] [xModifer] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
    //for the random print
    //nested loop which randomly fills the baorded with a 1 or 0 then prints it out
    void printRandBoard (int board[][], int rows, int columns){
        //board = new int[rows][columns];
        for(int yModifer= 0; yModifer<columns; yModifer++){
            for(int xModifer = 0; xModifer<rows; xModifer++){
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

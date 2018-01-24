/* 	------------------------------------------------
*  	8 Tiles UI
*
*  	Class: CS 342, Fall 2016
*  	System: OS X, IntelliJ IDEA
*  	Author Code Number: Holy
*  	------------------------------------------------
*/

import java.util.*;

// Board class that contains information and representation of the Board
public class Board {

    private int[] board;
    private int heuristicValue;

    /**  ------------------------------------------------
     *   Default construtor, makes a randomized Board
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public Board(){
        board = new int[Constants.BOARD_SIZE];

        for(int index = 0; index < Constants.BOARD_SIZE; index++){
            board[index] = index;
        }

        randomize();
        findHeuristicValue();
    }


    /**  ------------------------------------------------
     *   Constructor for board that makes a Board based
     *   on a passed in int array
     *
     *   Takes takes the int array to set the board to
     *   Returns nothing
     *   ------------------------------------------------
     */
    public Board(int[] numArray){
        board = numArray;
        findHeuristicValue();
    }


    /**  ------------------------------------------------
     *   Constructor for copying a Board to a new Board
     *
     *   Takes a Board to copy
     *   Returns nothing
     *   ------------------------------------------------
     */
    public Board(Board otherBoard){

        board = new int[Constants.BOARD_SIZE];

        for(int index = 0; index < Constants.BOARD_SIZE; index++){
            board[index] = otherBoard.board[index];
        }

        findHeuristicValue();
    }


    /**  ------------------------------------------------
     *   Constructor that makes a new Board based on a
     *   passed in Board and makes a move
     *
     *   Takes a Board to copy and an int to make a move
     *   Returns nothing
     *   ------------------------------------------------
     */
    public Board(Board oldBoard, int move){

        this(oldBoard);

        move(move);
        findHeuristicValue();
    }


    /**  ------------------------------------------------
     *   Prints the Board makes use of the toString method
     *   was put into a method for abstraction and modifying
     *   how the board displays while playing
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void displayBoard(){
        System.out.println(this);
    }


    /**  ------------------------------------------------
     *   Checks if a Board is solved
     *
     *   Takes no parameters
     *   Returns a boolean
     *   ------------------------------------------------
     */
    public boolean isSolved(){
        return heuristicValue == 0;
    }


    /**  ------------------------------------------------
     *   Checks if a board is a valid board: has 0 - 8
     *   exactly once each
     *
     *   Takes no parameters
     *   Returns boolean
     *   ------------------------------------------------
     */
    public boolean isValidBoard(){
        int[] count = new int[Constants.BOARD_SIZE];
        boolean result = true;
        for(int i = 0; i < Constants.BOARD_SIZE; i++){
            if(board[i] < 0 || board[i] >= Constants.BOARD_SIZE){
                result = false;
            }
            count[board[i]]++;
        }
        for(int j = 0; j < Constants.BOARD_SIZE; j++){
            if(count[j] != 1){
                result = false;
            }
        }
        return result;
    }


    /**  ------------------------------------------------
     *   Checks if a Board is the same as a passed in Board
     *
     *   Takes a Board parameter
     *   Returns boolean
     *   ------------------------------------------------
     */
    public boolean isTheSame(Board second){
        boolean result = true;
        for(int i = 0; i < Constants.BOARD_SIZE; i++){
            if(this.board[i] != second.board[i]){
                result = false;
            }
        }
        return result;
    }


    /**  ------------------------------------------------
     *   Checks if a move between two indexes is a valid
     *   move in the Board
     *
     *   Takes no parameters
     *   Returns boolean
     *   ------------------------------------------------
     */
    public boolean isValidSwap(int first, int second){
        if(second + 3 != first && second - 3 != first && second + 1 != first && second - 1 != first){
            return false;
        }
        if((second == 0 || second == 3 || second == 6) && first == second - 1){
            return false;
        }
        if((second == 2 || second == 5 || second == 8) && first == second + 1){
            return false;
        }
        return true;
    }


    /**  ------------------------------------------------
     *   Takes two values in the board and swaps their
     *   position if they are a valid swaps for 8-tile
     *
     *   Takes no parameters
     *   Returns the int of the move (-1 if not a valid
     *   move)
     *   ------------------------------------------------
     */
    public int swap(int val1, int blank){
        int first = findPositionOf(val1);
        int second = findPositionOf(blank);

        if(first == -1 || second == -1){
            System.out.println("*** Invalid move.  Please retry.");
            System.out.println();
            return -1;
        }

        if(!isValidSwap(first, second)){
            System.out.println("*** Invalid move.  Please retry.");
            System.out.println();
            return -1;
        }

        board[first] = blank;
        board[second] = val1;

        findHeuristicValue();
        return val1;
    }


    /**  ------------------------------------------------
     *   Calls swap to move a value in the Board and 0
     *   if it is a valid move in the current Board.
     *   Also checks if we should be solving or is a valid
     *   move.
     *
     *   Takes no parameters
     *   Returns result of performing the swap or exiting
     *   or starting the solving processes
     *   ------------------------------------------------
     */
    public int move(int val){

        if(val == Constants.letterS){
            System.out.println("Solving puzzle automatically...........................");
            return Constants.letterS;
        }
        else if(val < 0 || val > Constants.BOARD_SIZE - 1){
            System.out.println("*** Value invalid.  Please retry.");
            System.out.println();
            return - 1;
        }
        else if(val == 0){
            return 0;
        }
        else {
            return swap(val, 0);
        }

    }


    /**  ------------------------------------------------
     *   Randomizes the elements for a Board;
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void randomize(){
        Random randomGenerator = new Random();
        randomGenerator.setSeed(System.currentTimeMillis());
        for(int i = 0; i < Constants.BOARD_SIZE; i++){
            int index = randomGenerator.nextInt(i + 1);

            int value = board[index];
            board[index] = board[i];
            board[i] = value;
        }
    }

    /**  ------------------------------------------------
     *   Finds the index of the given value
     *
     *   Takes the int of a value in the board
     *   Returns -1 on error or the index of the value
     *   in the Board
     *   ------------------------------------------------
     */
    public int findPositionOf(int val){
        for(int position = 0; position < Constants.BOARD_SIZE; position++){
            if(board[position] == val) {
                return position;
            }
        }
        return -1;
    }


    /**  ------------------------------------------------
     *   Calculates a unique string for each Board
     *
     *   Takes no parameters
     *   Returns a string
     *   ------------------------------------------------
     */
    public String findBoardKey(){
        return new String(convertToCharArray());
    }


    /**  ------------------------------------------------
     *   Calculates the heuristicValue of a Board
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void findHeuristicValue(){

        heuristicValue = 0;

        for(int index = 0; index < Constants.BOARD_SIZE; index++){
            heuristicValue += (findColHeuristic(index) + findRowHeuristic(index));
        }

    }


    /**  ------------------------------------------------
     *   Finds how far from the current pos is from it
     *   proper row
     *
     *   Takes the int for the pos
     *   Returns the int of the calculated heuristicValue
     *   for the row
     *   ------------------------------------------------
     */
    public int findRowHeuristic(int pos){
        return Math.abs(findIndexRowVal(pos) - findRowVal(board[pos]));

    }


    /**  ------------------------------------------------
     *   Finds how far from the current pos is from it
     *   proper col
     *
     *   Takes the int for the pos
     *   Returns the int of the calculated heuristicValue
     *   for the col
     *   ------------------------------------------------
     */
    public int findColHeuristic(int pos){
        return Math.abs(findIndexColVal(pos) - findColVal(board[pos]));

    }


    /**  ------------------------------------------------
     *   Finds which rows the pos is in
     *
     *   Takes an int pos
     *   Returns the row pos is part of
     *   ------------------------------------------------
     */
    public int findRowVal(int pos){
        if(pos == 1 || pos == 2 || pos == 3){
            return 1;
        }
        else if(pos == 4 || pos == 5 || pos == 6){
            return 2;
        }
        else {
            return 3;
        }
    }


    /**  ------------------------------------------------
     *   Finds which columns the pos is in
     *
     *   Takes an int pos
     *   Returns the column pos is part of
     *   ------------------------------------------------
     */
    public int findColVal(int pos){
        if(pos == 1 || pos == 4 || pos == 7){
            return 1;
        }
        else if(pos == 2 || pos == 5 || pos == 8){
            return 2;
        }
        else {
            return 3;
        }
    }


    /**  ------------------------------------------------
     *   Finds which rows the pos is in
     *
     *   Takes an int pos
     *   Returns the row pos is supposed to be part of
     *   ------------------------------------------------
     */
    public int findIndexRowVal(int pos){
        if(pos == 0 || pos == 1 || pos == 2){
            return 1;
        }
        else if(pos == 3 || pos == 4 || pos == 5){
            return 2;
        }
        else {
            return 3;
        }
    }


    /**  ------------------------------------------------
     *   Finds which columns the pos is in
     *
     *   Takes an int pos
     *   Returns the column pos is supposed to be part of
     *   ------------------------------------------------
     */
    public int findIndexColVal(int pos){
        if(pos == 0 || pos == 3 || pos == 6){
            return 1;
        }
        else if(pos == 1 || pos == 4 || pos == 7){
            return 2;
        }
        else {
            return 3;
        }
    }

    /**  ------------------------------------------------
     *   Converts a board to an array of character for
     *   printing out and for converting to a string key
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public char[] convertToCharArray(){

        char[] boardChar = new char[Constants.BOARD_SIZE];

        for(int pos = 0; pos < Constants.BOARD_SIZE; pos++){
            if(board[pos] != 0){
                boardChar[pos] = (char)(board[pos] + Constants.numOffset);
            }
            else{
                boardChar[pos] = ' ';
            }
        }

        return boardChar;
    }


    /**  ------------------------------------------------
     *   Overrides the toString method for printing out
     *   the Board
     *
     *   Takes no parameters
     *   Returns a String
     *   ------------------------------------------------
     */
    public String toString(){

        char[] boardChar = convertToCharArray();

        return "\t" + boardChar[0] + " " + boardChar[1] + " " + boardChar[2] +
                "\n\t" + boardChar[3] + " " + boardChar[4] + " " + boardChar[5] +
                "\n\t" + boardChar[6] + " " + boardChar[7] + " " + boardChar[8] +
                "\nHeuristic Value: " +heuristicValue +"\n";

    }

    // getters and setters
    /**  ------------------------------------------------
     *   Gets the value of the heuristicValue
     *
     *   Takes no parameters
     *   Returns the heuristicValue
     *   ------------------------------------------------
     */
    public int getHeuristicValue() {
        return heuristicValue;
    }

    /**  ------------------------------------------------
     *   Gets the array of ints (board)
     *
     *   Takes no parameters
     *   Returns board
     *   ------------------------------------------------
     */
    public int[] getBoard() {
        return board;
    }

    /**  ------------------------------------------------
     *   Sets a board to a passed in array of ints
     *
     *   Takes in an array of ints
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void setBoard(int array[]){

        for(int index = 0; index < Constants.BOARD_SIZE; index++){
            board[index] = array[index];
        }
        findHeuristicValue();
    }

    /**  ------------------------------------------------
     *   Sets a board piece to the passed in value
     *
     *   Takes in 2 ints
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void setBoardPiece(int index, int piece){
        board[index] = piece;
    }
}
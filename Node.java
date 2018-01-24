/* 	------------------------------------------------
*  	8 Tiles UI
*
*  	Class: CS 342, Fall 2016
*  	System: OS X, IntelliJ IDEA
*  	Author Code Number: Holy
*  	------------------------------------------------
*/

import java.util.*;

// Node class that keeps track of a Board and its possible moves
// Implements the comparable interface to allow it to use the
// priority queue
public class Node implements Comparable<Node>{

    private Board previous;
    private Board current;
    private ArrayList<Board> nextMoves;
    private String boardKey;
    private int possibleMoves;

    /**  ------------------------------------------------
     *   Constructor, Makes a Node from a Board (the first
     *   Node)
     *
     *   Takes a Board
     *   Returns nothing
     *   ------------------------------------------------
     */
    public Node(Board currBoard){

        previous = new Board(currBoard);
        current = new Board(currBoard);

        boardKey = current.findBoardKey();

        nextMoves = new ArrayList<>();

        findOptions(currBoard);
    }


    /**  ------------------------------------------------
     *   Constructor, Makes a Node with current equal to
     *   the first Board and previous equal to the second
     *   Board
     *
     *   Takes a Board
     *   Returns nothing
     *   ------------------------------------------------
     */
    public Node(Board currBoard, Board prevBoard){

        previous = new Board(prevBoard);
        current = new Board(currBoard);

        boardKey = new String(current.convertToCharArray());

        nextMoves = new ArrayList<>();

        findOptions(currBoard);
    }


    /**  ------------------------------------------------
     *   Finds all possible moves from the passed in Board
     *   and stores them into nextMoves
     *
     *   Takes a Board to find
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void findOptions(Board currBoard){
        for(int i = 0; i < Constants.BOARD_SIZE; i++) {
            // checks each value in the board
            int first = current.findPositionOf(i);
            int second = current.findPositionOf(0);

            // checks if the move is valid before adding a board into the arrayList (like if they are adjacent and otherwise)
            if(current.isValidSwap(first, second)) {
                nextMoves.add(new Board(currBoard, i));
                possibleMoves++;
            }
        }

    }


    /**  ------------------------------------------------
     *   Compares the values of the current Board in given
     *   Nodes.
     *   Allows for nodes to be stored in a priority queue
     *
     *   Takes no parameters
     *   Returns an int
     *   ------------------------------------------------
     */
    @Override public int compareTo(Node otherNode){
        return current.getHeuristicValue() - otherNode.current.getHeuristicValue();
    }


    /**  ------------------------------------------------
     *   Allows the Node to be printed out for debugging
     *   purposes
     *
     *   Takes no parameters
     *   Returns the string to be printed for a Node
     *   ------------------------------------------------
     */
    @Override public String toString(){
        return "\nNum Moves: " +possibleMoves+ "\n\nCurrent:\n" + current + "\nMoves:\n" + nextMoves + "\n\n" + boardKey;
    }


    /**  ------------------------------------------------
     *   Checks if a Node is the first Node in the HashMap
     *
     *   Takes no parameters
     *   Returns boolean
     *   ------------------------------------------------
     */
    public boolean isFirst(){
        return current.isTheSame(previous);
    }


    /**  ------------------------------------------------
     *   Checks if a Node current is the solution
     *
     *   Takes no parameters
     *   Returns true if it is the solution, false otherwise
     *   ------------------------------------------------
     */
    public boolean isSolution(){
        return current.isSolved();
    }


    /**  ------------------------------------------------
     *   Finds the heurisitcValue of the Node's current
     *
     *   Takes no parameters
     *   Returns the current Board's heuristicValue
     *   ------------------------------------------------
     */
    public int findHeuristicValue(){
        return current.getHeuristicValue();
    }

    // getters
    /**  ------------------------------------------------
     *   Uses the Board positions to get the boardKey
     *   string for the HashMap of Nodes
     *
     *   Takes no parameters
     *   Returns String
     *   ------------------------------------------------
     */
    public String getBoardKey() {
        return boardKey;
    }


    /**  ------------------------------------------------
     *   Gets all possible next moves and stores them in
     *   an ArrayList that is passed
     *
     *   Takes no parameters
     *   Returns the ArrayList of all possible moves
     *   ------------------------------------------------
     */
    public ArrayList<Board> getNextMoves() {
        return nextMoves;
    }


    /**  ------------------------------------------------
     *   Gets the Node's current Board
     *
     *   Takes no parameters
     *   Returns the current Board
     *   ------------------------------------------------
     */
    public Board getCurrent() {
        return current;
    }


    /**  ------------------------------------------------
     *   Gets the Node's previous Board
     *
     *   Takes no parameters
     *   Returns the previous Board
     *   ------------------------------------------------
     */
    public Board getPrevious() {
        return previous;
    }

}
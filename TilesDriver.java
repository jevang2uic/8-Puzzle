/* 	------------------------------------------------
*  	8 Tiles UI
*
*  	Class: CS 342, Fall 2016
*  	System: OS X, IntelliJ IDEA
*  	Author Code Number: Holy
*  	------------------------------------------------
*/

/* 	------------------------------------------------
*
*   Implements a GUI to allow playing the Tiles Game
*   using a window instead of through the commandline
*
*  	------------------------------------------------
*/

import javax.swing.JFrame;

// Driver class for the program (cleaned up after shifting to GUI implementation)
public class TilesDriver{
    static Board board;
    static int turn;

    /**  ------------------------------------------------
     *   Prints out author info for the program
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public static void printOutAuthorInfo()
    {
        System.out.println();
        System.out.println("Author Code Number: Holy");
        System.out.println("Class: CS 342, Fall 2016");
        System.out.println("Program: #4, 8 Tiles UI");
        System.out.println();
    }


    /**  ------------------------------------------------
     *   Prints out game information
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public static void printGameInfo()
    {
        System.out.println();
        System.out.println("Welcome to the 8-tiles puzzle");
        System.out.println("Place the tiles in ascending numerical order. For each");
        System.out.println("move enter the piece to be moved into the blank square,");
        System.out.println("or 0 to exit the program.");
        System.out.println();
    }


    /**
     * Main class to run the program
     * @param args
     */
    public static void main(String[] args){

        printOutAuthorInfo();

        printGameInfo();

        board = new Board();

        BoardGridLayoutFrame boardDisplay = new BoardGridLayoutFrame(board);
        boardDisplay.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        boardDisplay.setSize( 600, 400 ); // set frame size
        boardDisplay.setVisible( true ); // display frame
        boardDisplay.GUIPrintIfSolved();


    }
}
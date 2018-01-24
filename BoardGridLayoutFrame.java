/* 	------------------------------------------------
*  	8 Tiles UI
*
*  	Class: CS 342, Fall 2016
*  	System: OS X, IntelliJ IDEA
*  	Author Code Number: Holy
*  	------------------------------------------------
*/

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

// class that handles the GUI manipulations for the program
public class BoardGridLayoutFrame extends JFrame{

    private JPanel gridJPanel;        // panel to hold the grid
    private JPanel controlJPanel;     // panel to hold the controls
    private JButton gridButtons[];    // array of buttons for the grid
    private JButton controlButtons[]; // array of buttons for the controls
    private Board board;              // Game board to track the updates to it
    private Container container;      // frame container
    private Boolean inputBoardMode;   // checking which mode to implement for the even handler
    private int boardCopier[];        // an array of ints to temporarily store the changes to the board for set board
    private int piecesSet;            // used to keep track of how many pieces have been set and which piece to set
    private ArrayList<Board> path;    // an arrayList of boards to display the solution path
    private int pathIndex;

    /**  ------------------------------------------------
     *   Default constructor for the BoardGridLayoutFrame
     *
     *   Takes a Board to set the Board of this class to
     *   Returns nothing
     *   ------------------------------------------------
     */
    public BoardGridLayoutFrame(Board firstBoard)
    {
        super( "8 Tile Board Layout" );
        gridJPanel = new JPanel();
        controlJPanel = new JPanel();
        GridLayout gridLayout = new GridLayout( 3, 3);
        gridJPanel.setLayout(gridLayout);
        controlJPanel.setLayout(new FlowLayout());
        container = getContentPane(); // get content pane
        gridButtons = new JButton[ Constants.BOARD_SIZE ];
        controlButtons = new JButton[4];
        board = firstBoard;
        inputBoardMode = false;
        boardCopier = new int[Constants.BOARD_SIZE];
        piecesSet = 0;
        path = new ArrayList<Board>();
        pathIndex = 0;

        initializeBoardCopier();

        int array[] = firstBoard.getBoard();
        for ( int count = 0; count < Constants.BOARD_SIZE; count++ )
        {
            gridButtons[ count ] = new JButton( Integer.toString(array[ count ]) );
            gridButtons[ count ].addActionListener( new buttonEventHandler( count )); // register listener
            int colorIndex = array[count];
            if(colorIndex == 0){
                colorIndex = 9;
            }
            gridButtons[count].setBackground(new Color(colorIndex*25, 108, 255));
            gridButtons[ count ].setOpaque(true);
            gridButtons[ count ].setBorderPainted(false);
            gridJPanel.add( gridButtons[ count ] );
        } // end for

        // control button set up
        controlButtons[0] = new JButton("Set Board");
        controlButtons[0].addActionListener(new buttonEventHandler( 0 ));
        controlJPanel.add(controlButtons[0]);

        controlButtons[1] = new JButton("Exit");
        controlButtons[1].addActionListener(new buttonEventHandler( 1 ));
        controlJPanel.add(controlButtons[1]);

        controlButtons[2] = new JButton("Solve");
        controlButtons[2].addActionListener(new buttonEventHandler( 2 ));
        controlJPanel.add(controlButtons[2]);

        controlButtons[3] = new JButton("New Game");
        controlButtons[3].addActionListener(new buttonEventHandler( 3 ));
        controlJPanel.add(controlButtons[3]);

        container.add(gridJPanel, BorderLayout.CENTER);
        container.add(controlJPanel, BorderLayout.SOUTH);

        // Note that buttons are added, but layout is a separate issue
    } // end GridLayoutFrame constructor

    /**  ------------------------------------------------
     *   Displays a victory message if the user has found
     *   the solution in some way
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void GUIPrintIfSolved(){
        if (board.isSolved()) {
            JOptionPane.showMessageDialog(null, "Congratulations! You won!", "Victory!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**  ------------------------------------------------
     *   Removes all text and color from buttons
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void clearButtons(){
        for(int count = 0; count < Constants.BOARD_SIZE; count++){
            gridButtons[count].setText("");
            gridButtons[count].setBackground(Color.WHITE);
        }
    }

    /**  ------------------------------------------------
     *   Sets up the values for setting the board up mode
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void initializeBoardCopier(){
        for(int count = 0; count < Constants.BOARD_SIZE; count++){
            boardCopier[count] = -1;
        }
        piecesSet = 0;
    }

    /**  ------------------------------------------------
     *   Update the buttons on the board with proper
     *   text and color
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void updateBoard(){
        int array[] = board.getBoard();
        for (int count = 0; count < Constants.BOARD_SIZE; count++) {
            gridButtons[count].setText(Integer.toString(array[count])); // update text
            int colorIndex = array[count];
            if(colorIndex == 0){
                colorIndex = 9;
            }
            gridButtons[count].setBackground(new Color(colorIndex*25, 108, 255)); // update color
        } // end for
    }

    /**  ------------------------------------------------
     *   Changes the board's value to match the values in
     *   the passed in board
     *
     *   Takes a board to copy values from
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void setBoard(Board secondBoard) {
        board.setBoard(secondBoard.getBoard());
        updateBoard();
    }

    /**  ------------------------------------------------
     *   Helper function to displaying the solution path
     *   uses the Timer class to delay displaying a solution
     *   to show the process of getting there
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void recDisplaySolution(){
        if(pathIndex < path.size()){
            setBoard(path.get(pathIndex));
            Timer timer = new Timer( 300, new ActionListener(){
                @Override
                public void actionPerformed( ActionEvent e ){
                    pathIndex++;
                    recDisplaySolution();
                }
            } );
            timer.setRepeats( false );
            timer.start();
        }
    }

    /**  ------------------------------------------------
     *   Makes use of the above helper function to print
     *   out all moves to the board solution if there is
     *   one, if not display text box saying so and show
     *   the best board found
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void displaySolution(){
        pathIndex = 0;
        SearchTree solution = new SearchTree(board);
        if(!solution.getSolutionFound()) {
            JOptionPane.showMessageDialog(null, "This Board is unsolvable! Too bad!\nHere is the best board though!\nBetter Luck next time!", "Bummer!", JOptionPane.INFORMATION_MESSAGE);
        }
        path = solution.findSolutionGUI();
        recDisplaySolution();
    }

    // event Handler subclass for handling above buttons
    private class buttonEventHandler implements ActionListener {

        int index;

        /**  ------------------------------------------------
         *   Default constructor for the buttonEventHandler
         *
         *   Takes an int to help keep track of button indexs
         *   for easy manipulation
         *   Returns nothing
         *   ------------------------------------------------
         */
        public buttonEventHandler(int index){
            this.index = index;
        }

        /**  ------------------------------------------------
         *   Button event handler, responds when a button is
         *   clicked with the associated actions
         *
         *   Takes an ActionEvent
         *   Returns nothing
         *   ------------------------------------------------
         */
        public void actionPerformed(ActionEvent event) {
            if(event.getActionCommand().equals("Set Board")){
                clearButtons();
                initializeBoardCopier();
                inputBoardMode = true;
            }
            else if (event.getActionCommand().equals("Exit")){
                System.exit(1);
            }
            else if (event.getActionCommand().equals("Solve")){
                if(inputBoardMode){
                    JOptionPane.showMessageDialog(null, "Enter a Board first!", "Wait!", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    displaySolution();
                }
            }
            else if (event.getActionCommand().equals("New Game")){
                setBoard( new Board());
                updateBoard();
            }
            else {
                if(inputBoardMode){
                    // input board pieces in proper order
                    if(event.getActionCommand().equals("")){
                        gridButtons[index].setText(Integer.toString(piecesSet));
                        boardCopier[index] = piecesSet;

                        int colorIndex = piecesSet;
                        if(colorIndex == 0){
                            colorIndex = 9;
                        }
                        gridButtons[index].setBackground(new Color(colorIndex*25, 108, 255));

                        piecesSet++;

                        if(piecesSet == Constants.BOARD_SIZE){
                            board.setBoard(boardCopier);
                            inputBoardMode = false;
                            GUIPrintIfSolved();
                            updateBoard();
                        }
                    }
                }
                else { // actually playing game
                    board.move(Integer.parseInt(event.getActionCommand())); // make move on the board
                    updateBoard();
                    container.validate(); // re-layout container
                    GUIPrintIfSolved();
                }
            }
        } // end method actionPerformed
    }
} // end class GridLayoutFrame


/* 	------------------------------------------------
*  	8 Tiles UI
*
*  	Class: CS 342, Fall 2016
*  	System: OS X, IntelliJ IDEA
*  	Author Code Number: Holy
*  	------------------------------------------------
*/

import java.util.*;

// Class to handle searching for the route to the best board/solution
public class SearchTree {

    private HashMap nodeHashMap;
    private PriorityQueue<Node> nodePriorityQueue;
    private boolean solutionFound;
    private Node bestNode;
    private int turn;
    //private BoardGridLayoutFrame display;
    private ArrayList<Board> solutionPath;

    /**  ------------------------------------------------
     *   Constructor for the SearchTree, starts solving
     *   from the passed in Board
     *
     *   Takes a Board
     *   Returns nothing
     *   ------------------------------------------------
     */
    public SearchTree(Board searchBoard){
        nodeHashMap = new HashMap();
        nodePriorityQueue = new PriorityQueue<>();
        bestNode = new Node(searchBoard);
        solutionFound = search(bestNode);
        turn = 1;
        //display = null;
        solutionPath = new ArrayList<>();
    }


    /**  ------------------------------------------------
     *   Takes a Node, puts all the possible moves in to
     *   the priority queue, if the Node's current Board
     *   is not in the HashMap and puts the Node into the
     *   HashMap. Also updates the bestNode found as needed.
     *
     *   Takes a Node to process
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void processNode(Node currentNode){
        nodeHashMap.put(currentNode.getBoardKey(), currentNode);
        if(currentNode.findHeuristicValue() < bestNode.findHeuristicValue()){
            bestNode = currentNode;
        }
        for(Board next: currentNode.getNextMoves()){
            Node nextNode = new Node(next, currentNode.getCurrent());

            if(!nodeHashMap.containsKey(nextNode.getBoardKey())) {
                nodePriorityQueue.add(nextNode);
            }
        }

    }


    /**  ------------------------------------------------
     *   Searches the possible moves from a passed in Node
     *   to to the solution if any
     *
     *   Takes no parameters
     *   Returns true if found a solution, false otherwise
     *   ------------------------------------------------
     */
    public boolean search(Node currentNode){

        boolean isFound = false;

        processNode(currentNode);
        if(currentNode.isSolution()){
            isFound = true;
        }

        while(nodePriorityQueue.size() != 0){
            Node next = nodePriorityQueue.poll();
            processNode(next);
            if(next.isSolution()){
                isFound = true;
            }
        }

        return isFound; // if we are out of places to go, (priority queue empty) there is no solution
    }


    /**  ------------------------------------------------
     *   Helper recursive function to print the solution
     *   in order. Takes the bestNode and prints the
     *   solution path to it if it is the solution
     *   or just the best node otherwise
     *
     *   Takes a Node (the bestNode)
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void printRecursive(Node lastNode){

        if(solutionFound) {
            if (!lastNode.isFirst()) {
                String nextKey = lastNode.getPrevious().findBoardKey();
                Node temp = (Node) nodeHashMap.get(nextKey);
                printRecursive(temp);
            }
            System.out.println(turn + ".");
            turn++;
            System.out.println(lastNode.getCurrent());
        }
        else{
            System.out.println("\n\nAll 181442 moves have been tried. \nThat puzzle is impossible to solve.  Best board found was: ");
            System.out.println(lastNode.getCurrent());
        }

    }


    /**  ------------------------------------------------
     *   Prints out the solution from the beginning to the
     *   bestNode if there is a solution, just the bestNode
     *   otherwise
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void printSolution(){
        printRecursive(bestNode);
    }

    /**  ------------------------------------------------
     *   Helper recursive function to print the solution
     *   in order. Takes the bestNode and prints the
     *   solution path to it if it is the solution
     *   or just the best node otherwise
     *
     *   Takes a Node (the bestNode)
     *   Returns nothing
     *   ------------------------------------------------
     */
    public void findRecursiveGUI(Node lastNode){

        if(solutionFound) {
            if (!lastNode.isFirst()) {
                String nextKey = lastNode.getPrevious().findBoardKey();
                Node temp = (Node) nodeHashMap.get(nextKey);
                solutionPath.add(lastNode.getCurrent());
                findRecursiveGUI(temp);
            }
            System.out.println(turn + ".");
            turn++;
            System.out.println(lastNode.getCurrent());


        }
        else{
            System.out.println("\n\nAll 181442 moves have been tried. \nThat puzzle is impossible to solve.  Best board found was: ");
            System.out.println(lastNode.getCurrent());
            solutionPath.add(lastNode.getCurrent());
        }

    }


    /**  ------------------------------------------------
     *   Prints out the solution from the beginning to the
     *   bestNode if there is a solution, just the bestNode
     *   otherwise
     *
     *   Takes no parameters
     *   Returns nothing
     *   ------------------------------------------------
     */
    public ArrayList<Board> findSolutionGUI(){;
        findRecursiveGUI(bestNode);
        Collections.reverse(solutionPath);
        return solutionPath;
    }

    // Getters and Setters

    /**  ------------------------------------------------
     *   Returns true if there is a solution found, false
     *   otherwise
     *
     *   Takes no parameters
     *   Returns a boolean of if there is a solution
     *   ------------------------------------------------
     */
    public boolean getSolutionFound(){
        return solutionFound;
    }

}
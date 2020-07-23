import java.nio.channels.FileLockInterruptionException;
import java.util.LinkedList;
import java.lang.Thread;
import java.lang.InterruptedException;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;

/**
 * The class of the lift object used to store the lift in the building.
 */
public class Lift
{
//Attributes
    private int currentFloor;
    private boolean goingUp;
    private Building building;
    private final int capacity;
    private int currentNumPeople;
    private boolean full;
    private boolean empty;
    private LinkedList<Person> peopleInLift;
    private Floor floor;
    private int moves;
    private JFrame f;
    private DisplayGraphics g;
    private boolean animate;

//Methods
    //Constructor
    /**
     * Constructor for objects of class Lift
     */
    public Lift(Building b, int cap, boolean a)
    {
        // initialise instance variables
        building = b;
        currentFloor = 0;
        findFloor();
        goingUp = true;
        currentNumPeople = 0;
        peopleInLift = new LinkedList<Person>();
        moves = 0;
        capacity = cap;
        animate = a;

    }
    //Setters
    /**
     * Sets currentFloor to the given parameter
     * @param newFloor The new value that currentFloor is 
     * going to be set to
     */
    public void setCurrentFloor(int newFloor){
        currentFloor = newFloor;
    }
    /**
     * Sets goingUp to the given parameter
     * @param newGoingUp The new value that goingUp is to be set to
     */
    public void setGoingUp(boolean newGoingUp){
        goingUp = newGoingUp;
    }
    //Getters
    /**
     * Returns whether the lift is going up
     * @return goingUp
     */
    public boolean getGoingUp(){
        return goingUp;
    }
    /**
     * Returns the floor number the lift is currently on
     * @return currentFloor
     */
    public int getCurrentFloor(){
        return currentFloor;
    }
    /**
     * Returns the current number of moves
     */
    public int getMoves(){
        return moves;
    }

    /**
     * Checks if the lift needs to change direction
     * @return canChange
     */
    public boolean checkIfChangeDirection(){
        boolean canChange = floor.getChangeDirection();
        return canChange;
    }
    /**
     * Changes the lift's current floor depending on what 
     * direction it is moving in. If it reaches the bound floors 
     * (top or bottom) it switches direction.
    */
    public void move() throws InterruptedException{
        if (checkIfChangeDirection()){
            goingUp ^= true;
        }

        if (goingUp) currentFloor++;
        else currentFloor--;
        findFloor();
        moves++;
        // f.getContentPane().revalidate();
        // f.getContentPane().repaint();
        if (animate){
            updateGraphics();
        }
    }
    /**
     * Changes the lift's current floor depending on what 
     * direction it is moving in
    */
    public void improvedMove(boolean peopleDirection) throws InterruptedException{
        if (currentFloor == 0) goingUp = true;
        else if (currentFloor == building.getTotalFloors() - 1) goingUp = false;
        else if (empty && !peopleDirection) goingUp ^= true;
        
        if (goingUp) currentFloor++;
        else currentFloor--;
        findFloor();
        moves++;
        // f.getContentPane().revalidate();
        // f.getContentPane().repaint();
        if (animate) {
            updateGraphics();
        }
    }
    /**
     * The first move the lift can make. Always makes the 
     * lift travel up by one.
     *
     */
    public void firstMove() throws InterruptedException{
        currentFloor++;
        findFloor();
        moves++;
        if (animate) {
            updateGraphics();
        }
    }
    /**
     * Adds a person to the lift, if the lift is not at capacity
     * @param p Person to be added to the lift
    */
    public void addPerson(Person p){
        checkIfFull();
        if (!full){
            peopleInLift.add(p);
            p.setInLift(true);
            checkCurrentNum();
        }
    }
    /**
     * Removes a person from the lift, if that person is in the lift
    */
    public void removePerson(Person p){
        if (p.getInLift()){
            peopleInLift.remove(p);
            checkCurrentNum();
            building.removePeople(1);
        }
    }
    /**
     * Checks whether the lift is at maximum capacity or not
    */
    public void checkIfFull(){
        full = currentNumPeople == capacity;
    }
    /**
     * Returns a boolean relating to whether the lift is full
     *
     * @return full
     */
    public boolean isFull(){
        return full;
    }
    /**
     * Checks whether the lift is at maximum capacity or not
    */
    public void checkIfEmpty(){
        empty = currentNumPeople == 0;
    }
    /**
     * Returns a boolean relating to whether the lift is full
     *
     * @return full
     */
    public boolean isEmpty(){
        return empty;
    }
    /**
     * Returns the linked list of people in the lift
     *
     * @return peopleinLift
     */
    public LinkedList<Person> getPeopleInLift(){
        return peopleInLift;
    }
    /**
     * Fills the lift. If people are on the lift's current floor and 
     * the lift isn't full, people are added to the lift until either 
     * the lift is full or there is no one left on the floor 
     * (whichever is first)
     *
     */
    public void baselineFillLift(){
        int availableSpaces = capacity - currentNumPeople;
        Floor currFloor = building.getFloor(currentFloor);
        LinkedList<Person> peopleOnFloor = currFloor.getPeopleOnFloor();

        LinkedList<Person> peopleToRemove = new LinkedList<Person>();
        for (Person p: peopleOnFloor){
            if (availableSpaces == 0){
                break;
            }
            addPerson(p);
            availableSpaces--;
            peopleToRemove.add(p);
        }

        if (peopleToRemove.size() != 0){
            for (Person p: peopleToRemove){
                currFloor.removePerson(p);
            }
        }

    }
    /**
     * Fills the lift. If there are people on the lift's current floor
     * and the lift isn't full, then people are added to the lift 
     * provided their destination is in the direction 
     * the lift is currenlty travelling in
     *
     */
    public void improvedFillLift(){
        int availableSpaces = capacity - currentNumPeople;
        Floor currFloor = building.getFloor(currentFloor);
        LinkedList<Person> peopleOnFloor = currFloor.getPeopleOnFloor();
        
        LinkedList<Person> peopleToRemove = new LinkedList<Person>();
        // System.out.println("Current floor: " + currentFloor);
        for (Person p: peopleOnFloor){
            if (availableSpaces == 0) break;
            
            int dest = p.getDestination();
            // System.out.println("Destination " + dest);
            if ((dest > currentFloor && goingUp) || (dest < currentFloor && !goingUp)) {
                addPerson(p);
                // System.out.println("Added");
                availableSpaces--;
                peopleToRemove.add(p);
            } else if ((currentFloor == 0) || (currentFloor == building.getTotalFloors() - 1)){
                addPerson(p);
                // System.out.println("Added - Special");
                availableSpaces--;
                peopleToRemove.add(p);
            }
        }  
        
        if (peopleToRemove.size() != 0){
            for (Person p: peopleToRemove){
                currFloor.removePerson(p);
            }
        }        
    }
    /**
     * Finds the floor object corresponding to the lift's current floor
     *
     */
    public void findFloor(){
        floor = building.getFloor(currentFloor);
    }
    /**
     * Returns the floor object corresponding to the lift's current floor
     *
     * @return floor
     */
    public Floor getActualFloor(){
        return floor;
    }
    /**
     * Returns the number of people currently in the lift
     *
     * @return int
     */
    public int getNumPeople(){
        return currentNumPeople;
    }
    /**
     * Finds the current number of people in the lift
     *
     */
    public void checkCurrentNum(){
        currentNumPeople = peopleInLift.size();
    }
    /**
     * Sets the JFrame that the lift simulation is running in
     *
     * @param frame The JFrame the simulation will run in
     */
    public void setJFrame(JFrame frame){
        f = frame;
    }
    /**
     * Sets the DisplayGraphics that the simulation will run in
     *
     * @param graphics The DisplayGraphics the simulation will run in
     */
    public void setGraphics(DisplayGraphics graphics){
        g = graphics;
    }
    /**
     * Repaints the DisplayGraphics the simulation is running in so the 
     * graphics get updated
     *
     */
    public void updateGraphics() throws InterruptedException{
        g.repaint();
        Thread.sleep(500);
    }
}

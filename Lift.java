import java.nio.channels.FileLockInterruptionException;
import java.util.LinkedList;

/**
 * The class of the lift object used to store the lift in the building.
 * @author Bethany Griffin
 * @version 1.5 - 05/03/2020
 */
public class Lift
{
//Attributes
    private int currentFloor;
    private boolean goingUp;
    private Building building;
    private final int capacity = 10;
    private int currentNumPeople;
    private boolean full;
    private LinkedList<Person> peopleInLift;
    private Floor floor;

//Methods
    //Constructor
    /**
     * Constructor for objects of class Lift
     */
    public Lift(Building b)
    {
        // initialise instance variables
        building = b;
        currentFloor = 0;
        findFloor();
        goingUp = true;
        currentNumPeople = 0;
        peopleInLift = new LinkedList<Person>();
    }
    //Setters
    public void setCurrentFloor(int newFloor){
        currentFloor = newFloor;
    }
    public void setgoingUp(boolean newGoingUp){
        goingUp = newGoingUp;
    }
    //Getters
    public boolean getgoingUp(){
        return goingUp;
    }
    public int getCurrentFloor(){
        return currentFloor;
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
     * Changes the lift's current floor depending on what direction it is moving in
    */
    public void baselineMove(){
        if (checkIfChangeDirection()){
            goingUp ^= true;
        }

        if (goingUp){
            currentFloor++;
            findFloor();
        } else{
            currentFloor--;
            findFloor();
        }
    }
    public void baselineFirstMove(){
        currentFloor++;
        findFloor();
    }
    /**
     * Adds a person to the lift, if the lift is not at capacity
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
    public boolean isFull(){
        return full;
    }
    public LinkedList<Person> getPeopleInLift(){
        return peopleInLift;
    }
    public void fillLift(){
        int availableSpaces = capacity - currentNumPeople;
        Floor currFloor = building.getFloor(currentFloor);
        LinkedList<Person> peopleOnFloor = new LinkedList<Person>();
        peopleOnFloor = currFloor.getPeopleOnFloor();

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
    public void findFloor(){
        floor = building.getFloor(currentFloor);
    }
    public Floor getActualFloor(){
        return floor;
    }
    public int getNumPeople(){
        return currentNumPeople;
    }
    public void checkCurrentNum(){
        currentNumPeople = peopleInLift.size();
    }
}

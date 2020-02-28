import java.nio.channels.FileLockInterruptionException;
import java.util.LinkedList;

/**
 * The class of the lift object used to store the lift in the building.
 * @author Bethany Griffin
 * @version 1.3 - 25/2/20
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
    public void move(){
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
    public void firstMove(){
        currentFloor++;
        findFloor();
    }
    /**
     * Adds a person to the lift, if the lift is not at capacity
    */
    public void addPerson(Person p){
        checkIfFull();
        if (!full){
            System.out.println("Added!");
            peopleInLift.add(p);
            p.setInLift(true);
            checkCurrentNum();
            // System.out.println("In: " + p.getInLift());
        }
    }
    /**
     * Removes a person from the lift, if that person is in the lift
    */
    public void removePerson(Person p){
        if (p.getInLift()){
            System.out.println("Removed!!");
            int n = building.getNumPeople();
            n--;
            peopleInLift.remove(p);
            checkCurrentNum();
            //building.setNumPeople(n);
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

        //System.out.println("Available spaces: " + availableSpaces);
        int spacesTaken = 0;
        // System.out.println(spacesTaken <= availableSpaces);
        // System.out.println(peopleOnFloor.size() != 0);
        while (spacesTaken <= availableSpaces && peopleOnFloor.size() != 0){
            // System.out.println("Number of people: " + peopleOnFloor.size());
            // System.out.println("Current floor number: " + currFloor.getFloorNumber());
            peopleOnFloor = currFloor.getPeopleOnFloor();
            // System.out.println("People on floor: " + peopleOnFloor);
            Person newPerson = peopleOnFloor.get(0);
            addPerson(newPerson);
            // System.out.println("In lift: " + newPerson.getInLift());
            checkCurrentNum();
            currFloor.removePerson(newPerson);
            spacesTaken++;
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

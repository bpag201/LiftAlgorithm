
/**
 * A place holder for a person using the lift system.
 * It records there current location and status along 
 * with their destination.
 */
public class Person
{
//Attributes
    private int currentFloor;
    private int destFloor;
    private boolean inLift;

//Methods
    //Constructors
    /**
     * Person Constructor
     *
     * @param cFloor The person's current floor
     * @param dFloor The person's destination floor
     */
    public Person(int cFloor, int dFloor)
    {
        // initialise instance variables
        currentFloor = cFloor;
        destFloor = dFloor;
        inLift = false;
    }
    //Setters
    /**
     * Sets the floor number person's current floor 
     * to the given parameter
     *
     * @param newFloor The new value for currentFloor
     */
    public void setCurrentFloor(int newFloor){
        currentFloor = newFloor;
    }
    /**
     * Sets whether the person is in the lift or not to the given 
     * parameter
     *
     * @param b The new value for inLift
     */
    public void setInLift(boolean b){
        inLift = b;
    }
    //Getters
    /**
     * Returns the floor number of the person's current floor
     *
     * @return currentFloor
     */
    public int getCurrentFloor(){
        return currentFloor;
    }
    /**
     * Returns the floor number of the person's destination
     *
     * @return destFloor
     */
    public int getDestination(){
        return destFloor;
    }
    /**
     * Returns the boolean relating to whether the person is in the 
     * lift or not
     *
     * @return inlift
     */
    public boolean getInLift(){
        return inLift;
    }
}

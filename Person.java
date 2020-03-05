
/**
 * A place holder for a person using the lift system.
 * It records there current location and status along with their destination.
 *
 * @author (Bethany Griffin)
 * @version (1.0 - 8/2/20)
 */
public class Person
{
//Attributes
    private int currentFloor;
    private int destFloor;
    private boolean inLift;

//Methods
    //Constructors
    public Person(int cFloor, int dFloor)
    {
        // initialise instance variables
        currentFloor = cFloor;
        destFloor = dFloor;
        inLift = false;
    }
    //Setters
    public void setCurrentFloor(int newFloor){
        currentFloor = newFloor;
    }
    public void setInLift(boolean b){
        inLift = b;
    }
    //Getters
    public int getCurrentFloor(){
        return currentFloor;
    }
    public int getDestination(){
        return destFloor;
    }
    public boolean getInLift(){
        return inLift;
    }
}

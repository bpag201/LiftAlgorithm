import java.util.Random;
import java.util.LinkedList;

/**
 * The class responsible for holding all of the methods 
 * and attributes relating to the building
 */
public class Building{
    private int totalFloors;
    private LinkedList<Floor> floors;
    private int numPeople;
    private boolean empty;

    /**
     * Building Constructor
     *
     * @param numFloors The number of floors the building has
     * @param numPeople The number of people to be spawned in
     * the building
     */
    public Building(int numFloors, int numPeople){
        this.numPeople = numPeople;
        empty = false;
        totalFloors = numFloors;
        floors = new LinkedList<Floor>();
        
        int peopleLeft = numPeople;
        Random r = new Random();
        Boolean finishedFloors = false;
        
        // Fills all of the floors with a random amount of people until all of the required
        // number of people have been allocated a floor
        while (peopleLeft != 0) {
            for (int i = 0; i < totalFloors; i++){
                int factor = (int)Math.ceil((double)peopleLeft / (totalFloors - i));
                boolean directFloor = i == 0 || i == totalFloors - 1;
                int peopleOnFloor = r.nextInt(factor + 1);
                if (!finishedFloors){
                    Floor myFloor = new Floor(peopleOnFloor, i, directFloor, totalFloors);
                    floors.add(myFloor);
                } else {
                    Floor myFloor = getFloor(i);
                    myFloor.addPeople(peopleOnFloor);
                }
                peopleLeft -= peopleOnFloor;
                if (peopleLeft == 0) break;
            }
            finishedFloors = true;
        }
    }

    /**
     * Returns the number of people currently in the building
     *
     * @return numPeople
     */
    public int getNumPeople(){
        return numPeople;
    }
    /**
     * Returns the list of floors in the building
     *
     * @return floors
     */
    public LinkedList<Floor> getFloors(){
        return floors;
    }
    /**
     * Returns the floor object corresponding to the given floor number
     *
     * @param index The floor number of the floor object to be found
     * @return The floor object corresponding to the given floor number
     */
    public Floor getFloor(int index){
        return floors.get(index);
    }
    /**
     * Returns the total number of floors in the building
     *
     * @return The total number of floors
     */
    public int getTotalFloors(){
        return totalFloors;
    }
    /**
     * Sets numPeople to the given parameter
     *
     * @param n The new number of people in the building
     */
    public void setNumPeople(int n){
        numPeople = n;
    }
    /**
     * Decreases the number of people in the building by the given 
     * amount
     *
     * @param n The number to decrease numPeople by
     */
    public void removePeople(int n){
        numPeople -= n;
    }
    /**
     * Returns the boolean corresponding to whether the 
     * building is empty
     *
     * @return Whether the building is empty or not
     */
    public boolean isEmpty(){
        if (numPeople == 0){
            empty = true;
        }
        return empty;
    }
}
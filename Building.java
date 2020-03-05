import java.util.Random;
import java.util.LinkedList;

/**
 *
 *
 * @author Bethany Griffin
 * @version 1.3 - 29/02/2020
 */
public class Building{
    private int totalFloors;
    private LinkedList<Floor> floors;
    private int numPeople;
    private boolean empty;

    public Building(int numFloors){
        numPeople = 0;
        empty = false;
        totalFloors = numFloors;
        floors = new LinkedList<Floor>();

        Random r = new Random();
        for (int i = 0; i < totalFloors; i++){
            boolean directFloor = i == 0 || i == totalFloors - 1;
            int peopleOnFloor = r.nextInt(10);
            numPeople += peopleOnFloor;
            Floor myFloor = new Floor(peopleOnFloor, i, directFloor, totalFloors);

            floors.add(myFloor);
        }
    }

    public int getNumPeople(){
        return numPeople;
    }
    public Floor getFloor(int index){
        return floors.get(index);
    }
    public void setNumPeople(int n){
        numPeople = n;
    }
    public void removePeople(int n){
        numPeople -= n;
    }
    public boolean isEmpty(){
        if (numPeople == 0){
            empty = true;
        }
        return empty;
    }
}
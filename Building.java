import java.util.Random;
import java.util.LinkedList;

/**
 *
 *
 * @author Bethany Griffin
 * @version 1.1 - 14/2/20
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
            int peopleOnFloor = r.nextInt(5);
            System.out.println("People on floor: " + peopleOnFloor);
            numPeople += peopleOnFloor;
            System.out.println("Total people in building: " + numPeople);
            Floor myFloor = new Floor(peopleOnFloor, i, directFloor);

            floors.add(myFloor);
            System.out.println("");
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
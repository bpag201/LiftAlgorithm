import java.util.Random;
import java.util.LinkedList;
/**
 *
 *
 * @author Bethany Griffin
 * @version 1.2 - 29/02/2020
 */
public class Floor{
    private int numPeople;
    private int floorNum;
    private int totalFloors;
    private LinkedList<Person> people;
    private boolean changeDirection;
    private boolean empty;

    public Floor(int pNum, int id, boolean b, int tFloors){
        numPeople = pNum;
        floorNum = id;
        changeDirection = b;
        totalFloors = tFloors;

        if (numPeople == 0){
            empty = true;
        } else{
            empty = false;
        }

        people = new LinkedList<Person>();

        Random r = new Random();
        for (int i = 0; i < numPeople; i++){
            int destination = (int)(1 + (totalFloors - 1) * r.nextFloat());
            Person myPerson = new Person(floorNum, destination);
            people.add(myPerson);
        }
    }

    public boolean getChangeDirection(){
        return changeDirection;
    }
    public LinkedList<Person> getPeopleOnFloor(){
        return people;
    }
    public int getFloorNumber(){
        return floorNum;
    }
    public void removePerson(Person p){
        people.remove(p);
        numPeople--;
    }
    public void checkIfEmpty(){
        empty = numPeople == 0;
    }
    public boolean isEmpty(){
        checkIfEmpty();
        return empty;
    }
    public int getNumPeople(){
        return numPeople;
    }

}
import java.util.Random;
import java.util.LinkedList;
/**
 * The class responsible for holding all of the methods 
 * and attributes relating to a floor
 */
public class Floor{
    private int numPeople;
    private int floorNum;
    private int totalFloors;
    private LinkedList<Person> people;
    private boolean changeDirection;
    private boolean empty;

    public Floor(int pNum, int id, boolean b, int tFloors){
        floorNum = id;
        changeDirection = b;
        totalFloors = tFloors;

        if (pNum == 0){
            empty = true;
        } else{
            empty = false;
        }

        people = new LinkedList<Person>();

        addPeople(pNum);
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
    public void addPeople(int num){
        numPeople += num;
        Random r = new Random();
        for (int i = 0; i < num; i++){
            int destination = (int)(1 + (totalFloors - 1) * r.nextFloat());
            // To ensure that the destination is not the same as
            // the floor they are currently on
            while (destination == floorNum) {
                destination = (int)(1 + (totalFloors - 1) * r.nextFloat());
            }
            Person myPerson = new Person(floorNum, destination);
            people.add(myPerson);
        }
        empty = false;
    }
}
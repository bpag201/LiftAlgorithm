import java.util.Random;
import java.util.LinkedList;
/**
 *
 *
 * @author (Bethany Griffin)
 * @version (1.1 - 14/2/20)
 */
public class Floor{
    private int numPeople;
    private int floorNum;
    private LinkedList<Person> people;
    private boolean changeDirection;
    private boolean empty;

    public Floor(int pNum, int id, boolean b){
        numPeople = pNum;
        floorNum = id;
        changeDirection = b;

        if (numPeople == 0){
            empty = true;
        } else{
            empty = false;
        }

        people = new LinkedList<Person>();

        Random r = new Random();
        for (int i = 0; i < numPeople; i++){
            int destination = (int)(1 + (6 - 1) * r.nextFloat());
            // System.out.println("Destination: " + destination);
            Person myPerson = new Person(floorNum, destination);
            // System.out.println("New person: " + myPerson);
            people.add(myPerson);
            // System.out.println("All people: " + people);
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
import java.util.LinkedList;

/**
 * The baseline algorithm for the project, the lift runs on a mechanical system
 * that can only change direction at either the highest floor or the lowest
 * floor.
 *
 * @author Bethany Griffin
 * @version 1.3 - 28/2/20
 */
public class Baseline{
    public static void main(String[] args){
        Building b = new Building(6);
        Lift l = new Lift(b);
        int numMoves = 0;

        //System.out.println("People: " + b.getNumPeople());
        while (!b.isEmpty()){
            
            LinkedList<Person> peopleToRemoveFromBuilding = new LinkedList<Person>();
            LinkedList<Person> peopleInLift = new LinkedList<Person>();
            peopleInLift = l.getPeopleInLift();
            // System.out.println("People in lift: " + peopleInLift);
            int numPeopleInLift = peopleInLift.size();
            int currFloor = l.getCurrentFloor();
            Floor actualFloor = l.getActualFloor();
            // System.out.println("Starting number of people: " + numPeopleOnFloor);

            System.out.println("People in lift: " + numPeopleInLift);

            if (numPeopleInLift != 0) {
                for (int i = 0; i < numPeopleInLift; i++){
                    Person p = peopleInLift.get(i);
                    int dest = p.getDestination();
                    // System.out.println("Destination: " + dest);
                    //System.out.println("Current floor: " + currFloor);

                    if (dest == currFloor){
                        // System.out.println(dest == currFloor);
                        // System.out.println("Before: " + b.getNumPeople());
                        peopleToRemoveFromBuilding.add(p);
                        // System.out.println("After: " + b.getNumPeople());
                    }
                }
                removePeopleFromBuilding(l, peopleToRemoveFromBuilding);
            }

            l.checkIfFull();
            System.out.println("Floor number: " + actualFloor.getFloorNumber());
            actualFloor.checkIfEmpty();
            System.out.println("Empty: " + actualFloor.isEmpty());
            if (!l.isFull() && !actualFloor.isEmpty()){
                //System.out.println("SHIT");
                l.fillLift();
            }

            // int currNumPeopleOnFloor = actualFloor.getNumPeople();
            // System.out.println("Current people on floor: " + currNumPeopleOnFloor);
            // int peopleToRemove = numPeopleOnFloor - (currNumPeopleOnFloor + l.getNumPeople());
            // System.out.println("People who left: " + peopleToRemove);
            // b.removePeople(peopleToRemove);

            System.out.println("People in building: " + b.getNumPeople());
            //System.out.println("People in lift (using currNumPeople): " + l.getNumPeople());
            //System.out.println("People in lift (using peopleInLift.size()):" + l.getPeopleInLift().size());
            // System.out.println("Moves: " + numMoves);
            System.out.println("People on floor: " + actualFloor.getNumPeople());
            System.out.println("People in lift: " + l.getPeopleInLift().size());

            if (numMoves == 0){
                l.firstMove();
            } else {
                l.move();
            }

            numMoves++;
            System.out.println("Current number of moves: " + numMoves);
            System.out.println("");
            assert (numMoves < 200): "ERROR: There is an infinte loop here";
            
        }
        System.out.println("Total Moves: " + numMoves);
    }

    
    public static void removePeopleFromBuilding(Lift l, LinkedList<Person> peopleToRemove){
        for (Person p: peopleToRemove){
            l.removePerson(p);
        }
    }

}
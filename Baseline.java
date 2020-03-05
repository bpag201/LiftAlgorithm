import java.util.LinkedList;

/**
 * The baseline algorithm for the project, the lift runs on a mechanical system
 * that can only change direction at either the highest floor or the lowest
 * floor.
 *
 * @author Bethany Griffin
 * @version 1.5 - 05/03/2020
 */
public class Baseline{
    public void run(int floors){
        Building b = new Building(10);
        Lift l = new Lift(b);
        int numMoves = 0;

        System.out.println("People: " + b.getNumPeople());
        while (!b.isEmpty()){

            LinkedList<Person> peopleToRemoveFromBuilding = new LinkedList<Person>();
            LinkedList<Person> peopleInLift = new LinkedList<Person>();
            peopleInLift = l.getPeopleInLift();
            int numPeopleInLift = peopleInLift.size();
            int currFloor = l.getCurrentFloor();
            Floor actualFloor = l.getActualFloor();

            if (numPeopleInLift != 0) {
                for (int i = 0; i < numPeopleInLift; i++){
                    Person p = peopleInLift.get(i);
                    int dest = p.getDestination();

                    if (dest == currFloor){
                        peopleToRemoveFromBuilding.add(p);
                    }
                }
                removePeopleFromBuilding(l, peopleToRemoveFromBuilding);
            }

            l.checkIfFull();

            actualFloor.checkIfEmpty();
            if (!l.isFull() && !actualFloor.isEmpty()){
                l.fillLift();
            }

            if (numMoves == 0){
                l.firstMove();
            } else {
                l.move();
            }

            numMoves++;

            assert (numMoves < 1000000): "ERROR: There is an infinte loop here";

        }
        System.out.println("Total Moves: " + numMoves);
    }

    public static void removePeopleFromBuilding(Lift l, LinkedList<Person> peopleToRemove){
        for (Person p: peopleToRemove){
            l.removePerson(p);
        }
    }

}
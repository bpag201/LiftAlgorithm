import java.util.LinkedList;
import java.lang.InterruptedException;
import java.io.IOException;
/**
 * The baseline algorithm for the project, the lift runs on a mechanical system
 * that can only change direction at either the highest floor or the lowest
 * floor.
 */
public class Baseline extends LiftAlgorithm{
    /**
     * Baseline Constructor
     *
     * @param floors The number of floors in the simulation
     * @param people The number of people in the simulation
     * @param a Whether the simulation will be animated or not
     */
    public Baseline(int floors, int people, boolean a){
        // c is the lift's capacity and will be 10% of all
        // people in the building
        int c = people / 10;
        finishedPeople = new LinkedList<Person>();
        b = new Building(floors, people);
        l = new Lift(b, c, a);
    }

    /**
     * Runs the simulation
     *
     */
    public void run() throws InterruptedException{
        final int TOTALFLOORS = b.getTotalFloors();
        final int TOTALPEOPLE = b.getNumPeople();
   
        // When the building is empty the simulation has finished
        while (!b.isEmpty()){

            LinkedList<Person> peopleToRemoveFromBuilding = new LinkedList<Person>();
            LinkedList<Person> peopleInLift = new LinkedList<Person>();
            peopleInLift = l.getPeopleInLift();
            int numPeopleInLift = peopleInLift.size();
            int currFloor = l.getCurrentFloor();
            Floor actualFloor = l.getActualFloor();

            if (numPeopleInLift != 0) {
                removeFromLift(numPeopleInLift, peopleToRemoveFromBuilding, peopleInLift, currFloor);
            }

            l.checkIfFull();
            actualFloor.checkIfEmpty();
            
            if (!l.isFull() && !actualFloor.isEmpty()){
                l.baselineFillLift();
            }

            if (l.getMoves() == 0){
                l.firstMove();
            } else {
                l.move();
            }
        }
        
        final int TOTALMOVES = l.getMoves();

        System.out.println("Baseline");
        System.out.println("People: " + TOTALPEOPLE);
        System.out.println("Floors: " + TOTALFLOORS);
        System.out.println("Total Moves: " + TOTALMOVES);
        
        // Saves the results of the simulation
        try{
            saveResults("resultsBaseline", TOTALMOVES, TOTALFLOORS, TOTALPEOPLE);
            System.out.println("Saved");
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
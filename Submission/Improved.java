import java.util.LinkedList;
import java.lang.InterruptedException;
import java.io.IOException;
/**
 * The class for my control system of the lift
 *
 */
public class Improved extends LiftAlgorithm{
    /**
     * Improved Constructor
     *
     * @param floors The number of floors in the building
     * @param people The number of people in the building
     */
    public Improved(int floors, int people, boolean a){
        // c is the lift's capacity and will be 10% of all
        // people in the building
        int c = people / 10; 
        finishedPeople = new LinkedList<Person>();
        b = new Building(floors, people);
        l = new Lift(b, c, a);
    }

    /**
     * Runs my control system
     *
     */
    public void run() throws InterruptedException{
        final int TOTALFLOORS = b.getTotalFloors();
        final int TOTALPEOPLE = b.getNumPeople();
        while (!b.isEmpty()){
            LinkedList<Person> peopleToRemoveFromBuilding = new LinkedList<Person>();
            LinkedList<Person> peopleInLift = new LinkedList<Person>();
            peopleInLift = l.getPeopleInLift();
            int numPeopleInLift = peopleInLift.size();
            int currFloor = l.getCurrentFloor();
            Floor actualFloor = l.getActualFloor();

            // Checks if there are any people to remove from the lift
            if (numPeopleInLift != 0) {
                removeFromLift(numPeopleInLift, peopleToRemoveFromBuilding, peopleInLift, currFloor);
            }

            l.checkIfFull();
            l.checkIfEmpty();
            actualFloor.checkIfEmpty();
            
            // If there is room in the lift and there are people on 
            // the floor then the lift is filled
            if (!l.isFull() && !actualFloor.isEmpty()){
                l.improvedFillLift();
            }
            
            // As the lift will always travel up on its first move, so
            // this checks if the lift is making its first move
            if (l.getMoves() == 0){
                l.firstMove();
            } else {
                boolean dir = peopleInDirection(l, b.getFloors());
                l.improvedMove(dir);
            }
        }
        
        final int TOTALMOVES = l.getMoves();
        System.out.println("Improved");
        System.out.println("People: " + TOTALPEOPLE);
        System.out.println("Floors: " + TOTALFLOORS);
        System.out.println("Total Moves: " + TOTALMOVES);
        
        // Saves the results to a csv file
        try{
            saveResults("resultsImproved", TOTALMOVES, TOTALFLOORS, TOTALPEOPLE);
            System.out.println("Saved");
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }
    
    /**
     * Returns a boolean relating to whether there are more people in the lift's current direction of travel
     *
     * @param l The lift used in the simulation
     * @param floors The list of floors within the simulation
     * @return Boolean
     */
    public boolean peopleInDirection(Lift l, LinkedList<Floor> floors){
        int curr = l.getCurrentFloor();
        int totalFloors = floors.size() - 1;
        boolean up = l.getGoingUp();
        
        // If people are found then true will be returned
        if (up){
            // Checks if there are any people above the lift
            for (int i = curr; i <= totalFloors; i++){
                Floor f = floors.get(i);
                if (!f.isEmpty()) return true;
            }
        } else {
            // Checks if there are any people below the lift
            for (int i = curr; i >= 0; i--){
                Floor f = floors.get(i);
                if (!f.isEmpty()) return true;
            }
        }
        
        // If no people are found then false is returned
        return false;
    }
}

import java.util.LinkedList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Abstract class LiftAlgorithm - Containing all of the attributes and behavours used by all of the lift algorithms
 * (the mechnical baseline and my improved algorithm)
 */
public abstract class LiftAlgorithm{
    // instance variables
    protected Lift l;
    protected Building b;
    protected LinkedList<Person> finishedPeople;
    
    /**
     * Returns the lift that the simulation is using
     *
     * @return l
     */
    public Lift returnLift(){
        return l;
    }
    
    /**
     * Returns the building that the simulation is taking place in
     *
     * @return b
     */
    public Building returnBuilding(){
        return b;
    }
    
    /**
     * Removes people from the lift if they are on their 
     * destination floor
     *
     * @param numPeopleInLift The total number of people in the lift
     * @param peopleToRemoveFromBuilding The list of people to 
     * remove from the building
     * @param peopleInLift The list of people in the lift
     * @param currFloor The floor number of the current floor
     */
    public void removeFromLift(int numPeopleInLift, LinkedList<Person> peopleToRemoveFromBuilding, LinkedList<Person> peopleInLift, int currFloor){
        for (int i = 0; i < numPeopleInLift; i++){
            Person p = peopleInLift.get(i);
            int dest = p.getDestination();

            if (dest == currFloor){
                peopleToRemoveFromBuilding.add(p);
            }
        }
        removePeopleFromBuilding(peopleToRemoveFromBuilding);
    }
    
    /**
     * Removes people from the building
     *
     * @param peopleToRemove List of people to be removed from the building
     */
    public void removePeopleFromBuilding(LinkedList<Person> peopleToRemove){
        for (Person p: peopleToRemove){
            l.removePerson(p);
            finishedPeople.add(p);
        }
    }

    /**
     * Saves the results of the simulation to a csv file of the 
     * given name. The number of people in the building and the 
     * number of moves it took to resolve are stored.
     * 
     *
     * @param fileName The name of the file the results are to be 
     * stored to
     * @param moves The number of moves it took to resolve 
     * the simulation
     * @param people The number of people that were in the building 
     * before the simulation started
     */
    public void saveResults(String fileName, float waitTime, int floors, int people) throws IOException{
        FileWriter fw = new FileWriter(fileName + ".csv", true);
        PrintWriter out = new PrintWriter(fw);

        out.print(floors + "," + waitTime + "," + people + "\n");
        
        out.flush();
        out.close();
        fw.close();       
    }
}

public class Improved{
    public void run(int floors){

    }

    public float[] getPriority(int floors, int aboveFloors, int belowFloors, int currFloor, Lift l, Building b){
        LinkedList<Person> peopleInLift = new LinkedList<Person>();
        peopleInLift = l.getPeopleInLift();
        float[] priorityArray = new float[floors];

        // Gathers the destinations of the people in the lift, adding this to the priority
        for (Person p: peopleInLift){
            int dest = p.getDestination();
            priorityArray[dest]++;
        }



        // Work out the priority for floors below
        float below = 0;
        for (int i = currFloor - 1; i >= 0; i--){
            below += priorityArray[i];
        }

        // Work out the priority for floors above
        float above = 0;
        for (int i = currFloor + 1; i <= floors; i++){
            above += priorityArray[i];
        }
    }
}
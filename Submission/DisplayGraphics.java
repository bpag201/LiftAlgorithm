import java.util.LinkedList;
import java.lang.InterruptedException;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class contains all of the graphics used.
 */
public class DisplayGraphics extends JPanel{
    private Lift l;
    private Building b;
    private Baseline base;
    private Improved im;
    private int floors;
    private int people;

    
    /**
     * DisplayGraphics Constructor
     *
     * @param numF The number of floors in the simulation
     * @param numP The number of people in the simulation
     * @param improved Whether the simulation will be the mechanical
     * baseline or the improved control system
     * @param animate Whether the simulation will be animated or not
     */
    public DisplayGraphics(int numF, int numP, boolean improved, boolean animate){
        floors = numF;
        people = numP;
        
        if (improved){
            im = new Improved(floors, people, animate);
            l = im.returnLift();
            b = im.returnBuilding();
        } else {
            base = new Baseline(floors, people, animate);
            l = base.returnLift();
            b = base.returnBuilding();
        }
    }
    
    /**
     * This paints the simulation onto the DisplayGraphics
     *
     * @param g The graphics used to paint my DisplayGraphics
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int currentFloor = l.getCurrentFloor();
        LinkedList<Floor> allFloors = b.getFloors();
        
        // Creates the individual floors
        for (int i = 0; i < floors; i++){
            g.setColor(Color.WHITE);
            g.fillRect(10, 30 * i, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(i) + ": " + String.valueOf(allFloors.get(i).getNumPeople()), 35, (30 * (floors - (i + 1)) + 15));
        }
        
        // Creates the lift
        g.setColor(Color.ORANGE);
        g.fillRect(10, 30 * (floors - (currentFloor + 1)), 20, 20);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(l.getNumPeople()), 15, (30 * (floors - (currentFloor + 1))) + 15);
    }
    
    /**
     * Returns the lift object used in the simulation
     *
     * @return l
     */
    public Lift getLift(){
        return l;
    }
    
    /**
     * Returns the baseline object used in the simulation
     *
     * @return base
     */
    public Baseline getBase(){
        return base;
    }
    
    /**
     * Returns the improved object used in the simulation
     *
     * @return im
     */
    public Improved getImproved(){
        return im;
    } 
    
    /**
     * This is the main method that is used to run the whole simulation
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        int f = 0, p = 0, ans = 0;
        
        InputStreamReader streamIn = new InputStreamReader(System.in);
        BufferedReader buffIn = new BufferedReader(streamIn);
        
        // Finds out which version of the simulation you want
        do {
                try {
                    System.out.println("Enter 1 for animated improved control system.");
                    System.out.println("Enter 2 for non-animated improved control system.");
                    System.out.println("Enter 3 for animated mechanical control system.");
                    System.out.println("Enter 4 for non-animated mechanical control system.");
                    String sAns = buffIn.readLine();
                    ans = Integer.parseInt(sAns);
                } catch (IOException e) {
                    System.out.println(e.toString());
                } catch (NumberFormatException e) {
                    System.out.println(e.toString());
                }
        }while (ans < 1 || ans > 4);
        
        // The maximum number of floors is dependent on which
        // selection the user makes. If the user decides to animate the 
        // simulation, they can only have a maximum of 10 floors.
        if (ans == 1 || ans == 3){
            // This is used if the user wants to animate, the maximum 
            // number of floors is 10
            do {
                try {
                    System.out.print("Enter number of floors (no more than 10): ");
                    String sf = buffIn.readLine();
                    f = Integer.parseInt(sf);
                } catch (IOException e) {
                    System.out.println(e.toString());
                } catch (NumberFormatException e) {
                    System.out.println(e.toString());
                }
            }while (f <= 0 || f > 10);
        } else {
            // If the user doesn't animate the simulation, there is 
            // no maximum number of floors
            do {
                try {
                    System.out.print("Enter number of floors: ");
                    String sf = buffIn.readLine();
                    f = Integer.parseInt(sf);
                } catch (IOException e) {
                    System.out.println(e.toString());
                } catch (NumberFormatException e) {
                    System.out.println(e.toString());
                }
            }while (f <= 0);
        }
        
        // This section allows the user to select how many people are in the 
        // simulation
        do {
                try {
                    System.out.print("Enter number of people: ");
                    String sp = buffIn.readLine();
                    p = Integer.parseInt(sp);
                } catch (IOException e) {
                    System.out.println(e.toString());
                } catch (NumberFormatException e) {
                    System.out.println(e.toString());
                }
        }while (p <= 0);

        // This is for running the improved control system with animation
        if (ans == 1){
            System.out.println("Improved");
            DisplayGraphics m = new DisplayGraphics(f, p, true, true);
            JFrame fr = new JFrame();
            fr.add(m);
            fr.setSize(400,400);
            // f.setLayout(null);
            fr.setVisible(true);
            m.getLift().setJFrame(fr);
            m.getLift().setGraphics(m);
            m.getImproved().run();
        // This is for running the improved control system with no
        // animation
        } else if (ans == 2){
            System.out.println("Improved");
            DisplayGraphics m = new DisplayGraphics(f, p, true, false);
            m.getImproved().run();
        // This is for running the mechanical control system with animation
        } else if (ans == 3){
            System.out.println("Baseline");
            DisplayGraphics m = new DisplayGraphics(f, p, false, true);
            JFrame fr = new JFrame();
            fr.add(m);
            fr.setSize(400,400);
            // f.setLayout(null);
            fr.setVisible(true);
            m.getLift().setJFrame(fr);
            m.getLift().setGraphics(m);
            m.getBase().run();
        // This is for running the mechanical control system with no
        // animation
        } else {
            System.out.println("Baseline");
            DisplayGraphics m = new DisplayGraphics(f, p, false, false);
            m.getBase().run();
        }
    }
}

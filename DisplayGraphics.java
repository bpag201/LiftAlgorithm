import java.awt.*;
import javax.swing.JFrame;

public class DisplayGraphics extends Canvas{

    public void paint(Graphics g) {
        int floors = 10;
        Baseline base = new Baseline();
        base.run(floors);

        for (int i = 1; i <= floors; i++){
            g.drawLine(10, 30 * i, 300, 30 * i);
        }

        for (int j = 0; j < floors; j++){
            g.drawRect(10, (floors - j) * 30, 20, 20);
        }

    }
    public static void main(String[] args) {
        DisplayGraphics m=new DisplayGraphics();
        JFrame f=new JFrame();
        f.add(m);
        f.setSize(400,400);
        //f.setLayout(null);
        f.setVisible(true);
    }

}

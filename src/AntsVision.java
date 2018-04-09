import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class AntsVision extends JPanel {

    private Vector<Ant> ants = null;


    public void paint(Ant a, Graphics g) {
        if (a instanceof AntWorker) {
            g.drawImage(AntWorker.img, (int) a.getx(), (int) a.gety(), this);
        } else {
            g.drawImage(AntWarrior.img, (int) a.getx(), (int) a.gety(), this);
        }
    }

    public void setMass(Vector<Ant> ans){
        ants = ans;
    }




    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (ants!=null){
            for (Ant a:ants){
                paint(a, g);
            }
        }
    }
}

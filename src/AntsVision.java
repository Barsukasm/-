import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AntsVision extends JPanel {

    public AntsVision(ArrayList<Ant> ants) {
        this.ants = ants;
    }

    private ArrayList<Ant> ants;

    public void paint(Graphics g, Ant a) {
        if (a instanceof AntWorker) {
            g.drawImage(AntWorker.img, a.getx(), a.gety(), this);
        } else {
            g.drawImage(AntWarrior.img, a.getx(), a.gety(), this);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        for (int i = 0; i < ants.size(); i++) {
            paint(g, ants.get(i));
        }

    }
}

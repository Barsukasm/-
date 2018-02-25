import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AntsVision extends JPanel {

    public AntsVision(ArrayList<Ant> ants, int e, boolean tv, boolean st) {
        this.ants = ants;
        if (st) {
            elapsed = e;
        }
        timeVisible = tv;
        status = st;
    }

    private ArrayList<Ant> ants;
    static int elapsed;
    boolean timeVisible = false;
    boolean status = false;

    public void paint(Graphics g, Ant a) {
        if (a instanceof AntWorker) {
            g.drawImage(AntWorker.img, a.getx(), a.gety(), this);
        } else {
            g.drawImage(AntWarrior.img, a.getx(), a.gety(), this);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (status) {
            if (timeVisible) {
                g.drawString("Time elapsed: " + elapsed, 5, 15);
            }
            this.setBackground(Color.WHITE);
            for (int i = 0; i < ants.size(); i++) {
                paint(g, ants.get(i));
            }
        } else {
            this.removeAll();
            int workers = 0;
            int warriors = 0;
            for (int i = 0; i < ants.size(); i++) {
                if (ants.get(i) instanceof AntWorker) {
                    workers++;
                } else {
                    warriors++;
                }
            }
            Font f1 = new Font("TimesRoman", Font.BOLD, 22);
            Font f2 = new Font("Calibri", Font.PLAIN, 12);
            Font f3 = new Font("Arial", Font.ITALIC, 16);

            g.drawString("Time elapsed: " + elapsed, this.getWidth() / 2 - 15, this.getHeight() / 2 - 20);
            g.setColor(Color.MAGENTA);
            g.setFont(f1);
            g.drawString("Ants generated: " + ants.size(), this.getWidth() / 2 - 15, this.getHeight() / 2);
            g.setColor(Color.BLUE);
            g.setFont(f2);
            g.drawString("Workers generated: " + workers, this.getWidth() / 2 - 15, this.getHeight() / 2 + 16);
            g.setColor(Color.RED);
            g.setFont(f3);
            g.drawString("Warriors generated: " + warriors, this.getWidth() / 2 - 15, this.getHeight() / 2 + 35);
        }

    }
}

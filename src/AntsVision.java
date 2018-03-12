import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class AntsVision extends JPanel {


    public void paint(Ant a) {
        if (a instanceof AntWorker) {
            getGraphics().drawImage(AntWorker.img, a.getx(), a.gety(), this);
        } else {
            getGraphics().drawImage(AntWarrior.img, a.getx(), a.gety(), this);
        }
    }

    public void repaint(Vector<Ant> ants){
        for (Ant a:ants){
            paint(a);
        }
    }


    /*public void paint(int total, int workers, int warriors, int elapsed) {
        this.removeAll();
        paintComponent(getGraphics());
        Font f1 = new Font("TimesRoman", Font.BOLD, 22);
        Font f2 = new Font("Calibri", Font.PLAIN, 12);
        Font f3 = new Font("Arial", Font.ITALIC, 16);

        Graphics g = getGraphics();
        g.drawString("Time elapsed: " + elapsed, this.getWidth() / 2 - 15, this.getHeight() / 2 - 20);
        g.setColor(Color.MAGENTA);
        g.setFont(f1);
        g.drawString("Ants generated: " + total, this.getWidth() / 2 - 15, this.getHeight() / 2);
        g.setColor(Color.BLUE);
        g.setFont(f2);
        g.drawString("Workers generated: " + workers, this.getWidth() / 2 - 15, this.getHeight() / 2 + 16);
        g.setColor(Color.RED);
        g.setFont(f3);
        g.drawString("Warriors generated: " + warriors, this.getWidth() / 2 - 15, this.getHeight() / 2 + 35);
    }*/

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

    }
}

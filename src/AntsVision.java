import javax.swing.*;
import java.awt.*;

public class AntsVision extends JPanel {


    public void paint(Ant a) {
        if (a instanceof AntWorker) {
            getGraphics().drawImage(AntWorker.img, a.getx(), a.gety(), this);
        } else {
            getGraphics().drawImage(AntWarrior.img, a.getx(), a.gety(), this);
        }
    }


    public void paint(int total, int workers, int warriors, int elapsed) {
        paintComponent(getGraphics());
        this.removeAll();
        Font f1 = new Font("TimesRoman", Font.BOLD, 22);
        Font f2 = new Font("Calibri", Font.PLAIN, 12);
        Font f3 = new Font("Arial", Font.ITALIC, 16);

        getGraphics().drawString("Time elapsed: " + elapsed, this.getWidth() / 2 - 15, this.getHeight() / 2 - 20);
        getGraphics().setColor(Color.MAGENTA);
        getGraphics().setFont(f1);
        getGraphics().drawString("Ants generated: " + total, this.getWidth() / 2 - 15, this.getHeight() / 2);
        getGraphics().setColor(Color.BLUE);
        getGraphics().setFont(f2);
        getGraphics().drawString("Workers generated: " + workers, this.getWidth() / 2 - 15, this.getHeight() / 2 + 16);
        getGraphics().setColor(Color.RED);
        getGraphics().setFont(f3);
        getGraphics().drawString("Warriors generated: " + warriors, this.getWidth() / 2 - 15, this.getHeight() / 2 + 35);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

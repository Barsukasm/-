package Client;

import javax.swing.*;
import java.awt.*;

public class ShowTime extends JPanel {

    boolean status = false;
    int elapsed = 0;

    public void show(boolean status, int elapsed) {
        this.status = status;
        this.elapsed = elapsed;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (status) {
            g.drawString("Time elapsed: " + elapsed, 0, 15);
        }
    }
}

import javax.swing.*;
import java.awt.*;

public class ShowTime extends JPanel {


    public void show(boolean status, int elapsed) {
        paintComponent(getGraphics());
        if (status) {
            getGraphics().drawString("Time elapsed: " + elapsed, 0, 15);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Habitat {

    private ArrayList<Ant> ants;

    private int n1,n2;
    private double p1,p2;
    private JFrame f = new JFrame("AntsSimulator");
    boolean generation = false;
    boolean timeVisible = false;

    public Habitat(int nw1, int nw2, double pw1, double pw2) {
        ants = new ArrayList<Ant>();
        n1 =nw1;
        n2 = nw2;
        p1 = pw1;
        p2 = pw2;
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(0, 0, (int) (dimension.getWidth() - 10), (int) (dimension.getHeight() - 10));
    }

    void update(double elapsed, double lastTime) {

        f.addKeyListener(new eHandler());

        if (generation) {
            if ((int) (elapsed) % n1 == 0 && (int) elapsed != (int) lastTime) {
                double e1 = Math.random();
                if (p1 >= e1 && e1 != 0) {
                    ants.add(new AntWorker((int) (Math.random() * f.getWidth() - 115), (int) (Math.random() * f.getHeight() - 115)));
                }
            }
            if ((int) (elapsed) % n2 == 0 && (int) elapsed != (int) lastTime) {
                double e2 = Math.random();
                if (p2 >= e2 && e2 != 0) {
                    ants.add(new AntWarrior((int) (Math.random() * f.getWidth() - 115), (int) (Math.random() * f.getHeight() - 115)));
                }
            }
        }


        AntsVision av = new AntsVision(ants, (int) elapsed, timeVisible, generation);
        f.add(av);
        f.setVisible(true);
        f.setExtendedState(Frame.MAXIMIZED_BOTH);

    }

    public class eHandler implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_B) {
                generation = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_T) {
                if (timeVisible) {
                    timeVisible = false;
                } else {
                    timeVisible = true;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_E) {
                generation = false;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }
    }

}



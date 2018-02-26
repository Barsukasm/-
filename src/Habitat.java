import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;

public class Habitat {

    private ArrayList<Ant> ants;

    private int n1,n2;
    private double p1,p2;
    private JFrame f = new JFrame("AntsSimulator");
    private boolean timeVisible = false;
    private int elapsed;
    private AntsVision av;
    private ShowTime st;
    private Timer timer;

    public Habitat(int nw1, int nw2, double pw1, double pw2) {
        ants = new ArrayList<Ant>();
        n1 =nw1;
        n2 = nw2;
        p1 = pw1;
        p2 = pw2;
        av = new AntsVision();
        st = new ShowTime();
    }

    public void start() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(0, 0, (int) (dimension.getWidth() - 10), (int) (dimension.getHeight() - 10));
        f.add(st);
        f.add(av);
        st.setBounds(0, 0, 110, 20);
        f.setVisible(true);
        f.setExtendedState(Frame.MAXIMIZED_BOTH);
        f.addKeyListener(new eHandler());
    }

    void update(double elapsed, double lastTime) {
        this.elapsed = (int) elapsed;
            if ((int) (elapsed) % n1 == 0 && (int) elapsed != (int) lastTime) {
                double e1 = Math.random();
                if (p1 >= e1 && e1 != 0) {
                    AntWorker aw = new AntWorker((int) (Math.random() * f.getWidth() - 115), (int) (Math.random() * f.getHeight() - 115));
                    ants.add(aw);
                    av.paint(aw);
                }
            }
            if ((int) (elapsed) % n2 == 0 && (int) elapsed != (int) lastTime) {
                double e2 = Math.random();
                if (p2 >= e2 && e2 != 0) {
                    AntWarrior awr = new AntWarrior((int) (Math.random() * f.getWidth() - 115), (int) (Math.random() * f.getHeight() - 115));
                    ants.add(awr);
                    av.paint(awr);
                }
            }
        st.show(timeVisible, this.elapsed);
    }



    public class eHandler implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_B) {
                timer = new Timer();
                timer.schedule(new Updater(Habitat.this), 0, 1000);
                av.repaint();
            }
            if (e.getKeyCode() == KeyEvent.VK_T) {
                if (timeVisible) {
                    timeVisible = false;
                } else {
                    timeVisible = true;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_E) {
                timer.cancel();
                timer = null;
                int warriors = 0, workers = 0;
                for (int i = 0; i < ants.size(); i++) {
                    if (ants.get(i) instanceof AntWarrior) {
                        warriors++;
                    } else {
                        workers++;
                    }
                }
                av.paint(ants.size(), workers, warriors, elapsed);
                ants.clear();
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



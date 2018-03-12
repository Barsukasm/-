import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.Vector;


public class Habitat {

    protected Vector<Ant> ants;

    private int n1,n2;
    private double p1,p2;
    protected GUIFrame f = new GUIFrame("AntsSimulator");
    protected boolean timeVisible = false;
    protected boolean running = false;
    protected int elapsed;
    protected Timer timer;
    private ModalDialog md = new ModalDialog(f);

    public Habitat(int nw1, int nw2, double pw1, double pw2) {
        ants = new Vector<Ant>();
        n1 =nw1;
        n2 = nw2;
        p1 = pw1;
        p2 = pw2;
    }



    public void start() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(0, 0, (int) (dimension.getWidth() - 10), (int) (dimension.getHeight() - 10));
        f.pack();
        f.setVisible(true);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        f.manager.addKeyEventDispatcher(new KDispatcher());
        f.start.addActionListener(new startButton());
        f.stop.addActionListener(new stopButton());
        f.showTimeOn.addActionListener(new showTimerListener());
        f.showTimeOff.addActionListener(new showTimerListener());
        md.ok.addActionListener(new dialogOk());
        f.startItem.addActionListener(new startMenuAction());
        f.stopItem.addActionListener(new stopMenuAction());
        f.timerItem.addActionListener(new showMenuAction());
        f.exitItem.addActionListener(new exitMenuAction());
        md.cancel.addActionListener(new dialogCancel());
    }

    void update(double elapsed, double lastTime) {
        this.elapsed = (int) elapsed;
            if ((int) (elapsed) % n1 == 0 && (int) elapsed != (int) lastTime) {
                double e1 = Math.random();
                if (p1 >= e1 && e1 != 0) {
                    AntWorker aw = new AntWorker((int) (Math.random() * f.av.getWidth() - 55), (int) (Math.random() * f.av.getHeight() - 55));
                    ants.add(aw);
                }
            }
            if ((int) (elapsed) % n2 == 0 && (int) elapsed != (int) lastTime) {
                double e2 = Math.random();
                if (p2 >= e2 && e2 != 0) {
                    AntWarrior awr = new AntWarrior((int) (Math.random() * f.av.getWidth() - 55), (int) (Math.random() * f.av.getHeight() - 55));
                    ants.add(awr);
                }
            }
        f.av.repaint(ants);
        f.st.show(timeVisible, this.elapsed);
    }



    public class KDispatcher implements KeyEventDispatcher{
        public boolean dispatchKeyEvent(KeyEvent e){
            if (e.getID()==KeyEvent.KEY_PRESSED){
            if (e.getKeyCode() == KeyEvent.VK_B&&!running) {
                n1 = Integer.parseInt(f.nWorkers.getText());
                n2 = Integer.parseInt(f.nWarriors.getText());
                p1 = (double) f.jbox.getSelectedIndex()/10;
                p2 = (double) f.jbox2.getSelectedIndex()/10;
                timer = new Timer();
                timer.schedule(new Updater(Habitat.this), 0, 1000);
                f.av.repaint();
                running = true;
                f.start.setEnabled(false);
                f.stop.setEnabled(true);
                f.startItem.setEnabled(false);
                f.stopItem.setEnabled(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_T) {
                if (timeVisible) {
                    timeVisible = false;
                    f.showTimeOff.setSelected(true);
                } else {
                    timeVisible = true;
                    f.showTimeOn.setSelected(true);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_E&&running) {
                int warriors = 0, workers = 0;
                for (int i = 0; i < ants.size(); i++) {
                    if (ants.get(i) instanceof AntWarrior) {
                        warriors++;
                    } else {
                        workers++;
                    }
                }
                md.setStats(ants.size(),workers,warriors,elapsed);
                timer.cancel();
                timer = null;
                md.setVisible(true);
            }
            }
            return false;
        }
    }

    public class startButton implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if (!running){
                n1 = Integer.parseInt(f.nWorkers.getText());
                n2 = Integer.parseInt(f.nWarriors.getText());
                p1 = (double) f.jbox.getSelectedIndex()/10;
                p2 = (double) f.jbox2.getSelectedIndex()/10;
                timer = new Timer();
                timer.schedule(new Updater(Habitat.this), 0, 1000);
                f.av.repaint();
                running = true;
                f.start.setEnabled(false);
                f.stop.setEnabled(true);
                f.startItem.setEnabled(false);
                f.stopItem.setEnabled(true);
            }
        }
    }

    public class stopButton implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if (running) {
                int warriors = 0, workers = 0;
                for (int i = 0; i < ants.size(); i++) {
                    if (ants.get(i) instanceof AntWarrior) {
                        warriors++;
                    } else {
                        workers++;
                    }
                }
                timer.cancel();
                timer = null;
                md.setStats(ants.size(),workers,warriors,elapsed);
                md.setVisible(true);
            }
        }
    }

    public class showTimerListener implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if (f.showTimeOn.isSelected()) {
                timeVisible = true;
            }
            if (f.showTimeOff.isSelected()){
                timeVisible = false;
            }
        }
    }

    public class dialogOk implements ActionListener{
        public void actionPerformed(ActionEvent e){
            f.av.repaint();
            f.st.repaint();
            ants.clear();
            running = false;
            f.start.setEnabled(true);
            f.stop.setEnabled(false);
            f.startItem.setEnabled(true);
            f.stopItem.setEnabled(false);
            md.setVisible(false);
        }
    }

    public class dialogCancel implements ActionListener{
        public void actionPerformed(ActionEvent e){
            timer = new Timer();
            Updater upd = new Updater(Habitat.this);
            upd.setStartTime((long) elapsed);
            timer.schedule(upd, 0, 1000);
            md.setVisible(false);
        }
    }

    public class startMenuAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (!running) {
                n1 = Integer.parseInt(f.nWorkers.getText());
                n2 = Integer.parseInt(f.nWarriors.getText());
                p1 = (double) f.jbox.getSelectedIndex()/10;
                p2 = (double) f.jbox2.getSelectedIndex()/10;
                timer = new Timer();
                timer.schedule(new Updater(Habitat.this), 0, 1000);
                f.av.repaint();
                running = true;
                f.start.setEnabled(false);
                f.stop.setEnabled(true);
                f.startItem.setEnabled(false);
                f.stopItem.setEnabled(true);
            }
        }
    }

    public class stopMenuAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (running) {
                int warriors = 0, workers = 0;
                for (int i = 0; i < ants.size(); i++) {
                    if (ants.get(i) instanceof AntWarrior) {
                        warriors++;
                    } else {
                        workers++;
                    }
                }
                md.setStats(ants.size(),workers,warriors,elapsed);
                timer.cancel();
                timer = null;
                md.setVisible(true);
            }
        }
    }

    public class showMenuAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (timeVisible) {
                timeVisible = false;
                f.showTimeOff.setSelected(true);
            } else {
                timeVisible = true;
                f.showTimeOn.setSelected(true);
            }
        }
    }

    public class exitMenuAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);
        }
    }

}



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;


public class Habitat {

    public Vector<Ant> ants;
    private int n1,n2;
    private double p1,p2;
    protected GUIFrame f = new GUIFrame("AntsSimulator");
    protected boolean timeVisible = false;
    protected boolean running = false;
    protected int elapsed;
    protected HashSet<Integer> ids;
    protected TreeMap<Integer,Integer> timeTree;
    protected Timer timer;
    private ModalDialog md = new ModalDialog(f);
    private CurrentObjDia curObjs = new CurrentObjDia(f);
    public WarriorAI warriorAI;
    public WorkerAI workerAI;

    public Habitat(int nw1, int nw2, double pw1, double pw2) {
        ants = new Vector<>();
        ids = new HashSet<>();
        timeTree = new TreeMap<>();
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
        f.showObjs.addActionListener(new curObjsButton());
        curObjs.ok.addActionListener(new curObjsOK());
        md.addWindowListener(new modalDialogWindowListener());
        curObjs.addWindowListener(new curObjWindowListener());
    }

    public void dying(){
        //Удаление старых объектов
        int ct = 0, a;
        Ant ta = null;
        Iterator<Integer> iter = ids.iterator();
        while (iter.hasNext()){
            a = iter.next();
            for (Ant m:ants){
                if (m.id==a){
                    ta = m;
                    ct = timeTree.get(a);
                    break;
                }
            }
            if (ta instanceof AntWorker) {
                if ((this.elapsed - ct) > AntWorker.lifeTime) {
                    ants.remove(ta);
                    timeTree.remove(a);
                    iter.remove();
                }
            } else{
                if (ta instanceof AntWarrior &&(this.elapsed-ct)>AntWarrior.lifeTime){
                    ants.remove(ta);
                    timeTree.remove(a);
                    iter.remove();
                }
            }
        }
    }

    public void generateNew(double lastTime){
        //Генерация новых объектов
        if ((int) (elapsed) % n1 == 0 && (int) elapsed != (int) lastTime) {
            double e1 = Math.random();
            if (p1 >= e1 && e1 != 0) {
                AntWorker aw = new AntWorker((int) (Math.random() * f.av.getWidth() - 55), (int) (Math.random() * f.av.getHeight() - 55), this.elapsed);
                boolean in = ids.add(aw.id);
                if(!in){
                    int n = 1;
                    while (!in||n<Ant.diapason){
                        aw.reroll();
                        in = ids.add(aw.id);
                        n++;
                    }
                }
                if(in){
                    ants.add(aw);
                    timeTree.put(aw.id,aw.spawnTime);
                }
            }
        }
        if ((int) (elapsed) % n2 == 0 && (int) elapsed != (int) lastTime) {
            double e2 = Math.random();
            if (p2 >= e2 && e2 != 0) {
                AntWarrior awr = new AntWarrior((int) (Math.random() * f.av.getWidth() - 55), (int) (Math.random() * f.av.getHeight() - 55),this.elapsed);
                boolean in = ids.add(awr.id);
                if(!in){
                    int n = 1;
                    while (!in||n<Ant.diapason){
                        awr.reroll();
                        in = ids.add(awr.id);
                        n++;
                    }
                }
                if(in){
                    ants.add(awr);
                    timeTree.put(awr.id,awr.spawnTime);
                }
            }
        }
    }

    void update(double elapsed, double lastTime) {
        synchronized (ants){
        this.elapsed = (int) elapsed;
        dying();
        generateNew(lastTime);
        ants.notifyAll();
        }
        f.av.setMass(ants);
        f.av.repaint();
        f.st.show(timeVisible, this.elapsed);
    }

    public void command_Start(){
        n1 = Integer.parseInt(f.nWorkers.getText());
        n2 = Integer.parseInt(f.nWarriors.getText());
        AntWorker.lifeTime = Integer.parseInt(f.workersLifeTime.getText());
        AntWarrior.lifeTime = Integer.parseInt(f.warriorsLifeTime.getText());
        if(AntWorker.lifeTime == 0) AntWorker.lifeTime = 1;
        if (AntWarrior.lifeTime == 0) AntWarrior.lifeTime = 1;
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
        warriorAI = new WarriorAI(ants);
        warriorAI.start();
        workerAI = new WorkerAI(ants);
        workerAI.start();
    }

    public void command_stop(){
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
        if (warriorAI != null){
            warriorAI.going = false;
            warriorAI = null;
        }
        if (workerAI != null){
            workerAI.going = false;
            workerAI = null;
        }
        md.setVisible(true);
    }

    public void command_time(){
        if (timeVisible) {
            timeVisible = false;
            f.showTimeOff.setSelected(true);
        } else {
            timeVisible = true;
            f.showTimeOn.setSelected(true);
        }
    }

    public void command_resume(){
        if(warriorAI == null){
            warriorAI = new WarriorAI(ants);
            warriorAI.start();
        }
        if(workerAI == null) {
            workerAI = new WorkerAI(ants);
            workerAI.start();
        }
        timer = new Timer();
        Updater upd = new Updater(Habitat.this);
        upd.setStartTime((long) elapsed);
        timer.schedule(upd, 0, 1000);

    }


    public class KDispatcher implements KeyEventDispatcher{
        public boolean dispatchKeyEvent(KeyEvent e){
            if (e.getID()==KeyEvent.KEY_PRESSED){
            if (e.getKeyCode() == KeyEvent.VK_B&&!running) {
                command_Start();
            }
            if (e.getKeyCode() == KeyEvent.VK_T) {
                command_time();
            }
            if (e.getKeyCode() == KeyEvent.VK_E&&running) {
                command_stop();
            }
            }
            return false;
        }
    }

    public class startButton implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if (!running){
                command_Start();
            }
        }
    }

    public class stopButton implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if (running) {
                command_stop();
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
            command_resume();
            md.setVisible(false);
        }
    }

    public class startMenuAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (!running) {
                command_Start();
            }
        }
    }

    public class stopMenuAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (running) {
                command_stop();
            }
        }
    }

    public class showMenuAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            command_time();
        }
    }

    public class exitMenuAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);
        }
    }

    public class curObjsButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (running) {
                timer.cancel();
                timer = null;
                if (warriorAI != null){
                    warriorAI.going = false;
                    warriorAI = null;
                }
                if (workerAI != null){
                    workerAI.going = false;
                    workerAI = null;
                }
                curObjs.setMsg(ants);
                curObjs.setVisible(true);
            }
        }
    }

    public class curObjsOK implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            command_resume();
            curObjs.setVisible(false);
        }
    }

    public class curObjWindowListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent windowEvent) {
            command_resume();
            curObjs.setVisible(false);
        }
    }

    public class modalDialogWindowListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent windowEvent) {
            command_resume();
            md.setVisible(false);
        }
    }
}



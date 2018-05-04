import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
    protected Properties props;
    private ModalDialog md = new ModalDialog(f);
    public Console cd = new Console(f, this);
    private CurrentObjDia curObjs = new CurrentObjDia(f);
    public WarriorAI warriorAI;
    public WorkerAI workerAI;
    public ConsManager cm = new ConsManager(this);
    public boolean kBlocked = false;


    Properties defProps(){
        Properties properties = new Properties();
        properties.setProperty("BornWorker","3");
        properties.setProperty("BornWarrior","4");
        properties.setProperty("ChanceWorker","0.8");
        properties.setProperty("ChanceWarrior","0.5");
        properties.setProperty("WorkerLifetime","10");
        properties.setProperty("WarriorLifetime","5");
        properties.setProperty("WorkerPrior","1");
        properties.setProperty("WarriorPrior", "1");
        return properties;
    }



    public Habitat(int nw1, int nw2, double pw1, double pw2) {
        ants = new Vector<>();
        ids = new HashSet<>();
        timeTree = new TreeMap<>();
        n1 =nw1;
        n2 = nw2;
        p1 = pw1;
        p2 = pw2;
    }

    void setProper(Properties properties){
        n1 = Integer.parseInt(properties.getProperty("BornWorker"));
        f.nWorkers.setText(String.valueOf(n1));
        n2 = Integer.parseInt(properties.getProperty("BornWarrior"));
        f.nWarriors.setText(String.valueOf(n2));
        p1 = Double.parseDouble(properties.getProperty("ChanceWorker"));
        f.jbox.setSelectedIndex((int)(p1*10));
        p2 = Double.parseDouble(properties.getProperty("ChanceWarrior"));
        f.jbox2.setSelectedIndex((int)(p2*10));
        AntWorker.lifeTime = Integer.parseInt(properties.getProperty("WorkerLifetime"));
        f.workersLifeTime.setText(String.valueOf(AntWorker.lifeTime));
        AntWarrior.lifeTime = Integer.parseInt(properties.getProperty("WarriorLifetime"));
        f.warriorsLifeTime.setText(String.valueOf(AntWarrior.lifeTime));
        f.workersprior.setSelectedIndex(Integer.parseInt(properties.getProperty("WorkerPrior")));
        f.warriorsprior.setSelectedIndex(Integer.parseInt(properties.getProperty("WarriorPrior")));
    }

    public void start() {
        props = new Properties();
        try {
            props.load(new FileInputStream(new File("config.properties")));
        } catch (IOException e){
            try{
                defProps().store(new FileOutputStream(new File("config.properties")),"config");
                props = defProps();
            } catch (IOException ex){}
        }
        setProper(props);
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
        f.consoleItem.addActionListener(new consoleMenuAction());
        f.exitItem.addActionListener(new exitMenuAction());
        md.cancel.addActionListener(new dialogCancel());
        f.showObjs.addActionListener(new curObjsButton());
        curObjs.ok.addActionListener(new curObjsOK());
        md.addWindowListener(new modalDialogWindowListener());
        curObjs.addWindowListener(new curObjWindowListener());
        f.warriorsAIButton.addActionListener(new warriorsAISwitcher());
        f.workerAIButton.addActionListener(new workersAISwitcher());
        f.saveItem.addActionListener(saveListener);
        f.loadItem.addActionListener(loadListener);
        f.addWindowListener(new endOnClose());
        cd.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                kBlocked = false;
                cd.clear();
            }
        });
        try{
            cm.setStream(new DataInputStream(new PipedInputStream(cd.getStream())));
        }catch (IOException e){}
        cm.start();
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
        int wp = f.workersprior.getSelectedIndex();
        int wap = f.warriorsprior.getSelectedIndex();
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
        switch (wp){
            case 0:
                workerAI.setPriority(Thread.MIN_PRIORITY);
                break;
            case 1:
                workerAI.setPriority(Thread.NORM_PRIORITY);
            case 2:
                workerAI.setPriority(Thread.MAX_PRIORITY);
                break;
        }
        switch (wap){
            case 0:
                warriorAI.setPriority(Thread.MIN_PRIORITY);
                break;
            case 1:
                warriorAI.setPriority(Thread.NORM_PRIORITY);
            case 2:
                warriorAI.setPriority(Thread.MAX_PRIORITY);
                break;
        }
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
        if (warriorAI != null){
            warriorAI.pause = true;
        }
        if (workerAI != null){
            workerAI.pause = true;
        }
        timer.cancel();
        timer = null;
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
        if (warriorAI != null){
            warriorAI.pause = false;
        }
        if (workerAI!= null){
            workerAI.pause = false;
        }
        timer = new Timer();
        Updater upd = new Updater(Habitat.this);
        upd.setStartTime((long) elapsed);
        timer.schedule(upd, 0, 1000);

    }


    public class KDispatcher implements KeyEventDispatcher{
        public boolean dispatchKeyEvent(KeyEvent e){
            if (!kBlocked){
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
            if (warriorAI != null){
                warriorAI.going = false;
                warriorAI = null;
            }
            if (workerAI != null){
                workerAI.going = false;
                workerAI = null;
            }
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

    public class consoleMenuAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            kBlocked = true;
            cd.setVisible(true);
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
                    warriorAI.pause = true;
                }
                if (workerAI!=null){
                    workerAI.pause = true;
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

    public class endOnClose extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent windowEvent) {
            props.setProperty("BornWorker",String.valueOf(n1));
            props.setProperty("BornWarrior",String.valueOf(n2));
            props.setProperty("ChanceWorker",String.valueOf(p1));
            props.setProperty("ChanceWarrior",String.valueOf(p2));
            props.setProperty("WorkerLifetime",String.valueOf(AntWorker.lifeTime));
            props.setProperty("WarriorLifetime",String.valueOf(AntWarrior.lifeTime));
            props.setProperty("WorkerPrior",String.valueOf(f.workersprior.getSelectedIndex()));
            props.setProperty("WarriorPrior", String.valueOf(f.warriorsprior.getSelectedIndex()));
            try {
                props.store(new FileOutputStream(new File("config.properties")),"config");
            } catch (IOException exp){}

            if (warriorAI != null){
                warriorAI.going = false;
                warriorAI = null;
            }
            if (workerAI != null){
                workerAI.going = false;
                workerAI = null;
            }
            if(cm!=null) cm.running = false;
        }
    }

    public class modalDialogWindowListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent windowEvent) {
            command_resume();
            md.setVisible(false);
        }
    }

    public class workersAISwitcher implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (workerAI!=null){
                if (!workerAI.pause) {
                    workerAI.pause = true;
                    f.workerAIButton.setText("Workers AI off");
                } else {
                    workerAI.pause = false;
                    f.workerAIButton.setText("Workers AI on");
                }
            }
        }
    }

    public class warriorsAISwitcher implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (warriorAI != null){
                if (!warriorAI.pause) {
                    warriorAI.pause = true;
                    f.warriorsAIButton.setText("Warriors AI off");
                } else {
                    warriorAI.pause = false;
                    f.warriorsAIButton.setText("Warriors AI on");
                }
            }
        }
    }


    private ActionListener saveListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileSave = new JFileChooser();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ants.txt"));
                for (Ant ant : ants) {
                    oos.writeObject(ant); // Сериализация
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    };

    private ActionListener loadListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (running){
                command_stop();
                f.av.repaint();
                f.st.repaint();
                ants.clear();
                if (warriorAI != null){
                    warriorAI.going = false;
                    warriorAI = null;
                }
                if (workerAI != null){
                    workerAI.going = false;
                    workerAI = null;
                }
                running = false;
                f.start.setEnabled(true);
                f.stop.setEnabled(false);
                f.startItem.setEnabled(true);
                f.stopItem.setEnabled(false);
            }
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showDialog(null, "Open file");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                    while (true) {
                        Object obj = null;
                        try {
                            obj = ois.readObject();
                        } catch (IOException | ClassNotFoundException ex) {
                            break;
                        }
                        if (!(obj instanceof Ant)) {
                            continue;
                        }
                        Ant ant = (Ant) obj;
                        ant.spawnTime = 0;
                        ants.add(ant);
                        ids.add(ant.id);
                        timeTree.put(ant.id,ant.spawnTime);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    };
}


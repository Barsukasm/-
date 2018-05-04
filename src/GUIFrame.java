import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUIFrame extends JFrame {

    JPanel mainPanel = new JPanel();
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p21 = new JPanel();
    JPanel p22 = new JPanel();
    JPanel p23 = new JPanel();
    JPanel p231 = new JPanel();
    JPanel p232 = new JPanel();
    JPanel p24 = new JPanel();
    JPanel p25 = new JPanel();
    JPanel p251 = new JPanel();
    JPanel p26 = new JPanel();
    JPanel p27 = new JPanel();
    JPanel p28 = new JPanel();
    JPanel p29 = new JPanel();
    JButton start = new JButton("Start");
    JButton stop = new JButton("Stop");
    AntsVision av = new AntsVision();
    ShowTime st = new ShowTime();
    KeyboardFocusManager manager;
    JRadioButton showTimeOn = new JRadioButton("Show simulation time");
    JRadioButton showTimeOff = new JRadioButton("Do not show simulation time", true);
    ButtonGroup showTimeGr = new ButtonGroup();
    JMenuBar jbar = new JMenuBar();
    JMenu failMenu = new JMenu("File");
    JMenuItem startItem = new JMenuItem("Start");
    JMenuItem stopItem = new JMenuItem("Stop");
    JMenuItem timerItem = new JMenuItem("Show/Hide time");
    JMenuItem consoleItem = new JMenuItem("Console");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem loadItem = new JMenuItem("Load");
    JMenuItem exitItem = new JMenuItem("Exit");
    JTextField nWorkers = new JTextField(10);
    JTextField nWarriors = new JTextField(10);
    JTextField workersLifeTime = new JTextField(10);
    JTextField warriorsLifeTime = new JTextField(10);
    String[] pers = { "0%","10%","20%","30%","40%","50%","60%","70%","80%","90%","100%"};
    JComboBox jbox = new JComboBox(pers);
    JComboBox jbox2 = new JComboBox(pers);
    JLabel q1 = new JLabel("Workers spawn frequency:");
    JLabel q2 = new JLabel("Warriors spawn frequency:");
    JLabel chance1 = new JLabel("Workers spawn chance:");
    JLabel chance2 = new JLabel("Warriors spawn chance:");
    JLabel lifeTimeWk = new JLabel("Workers lifetime:");
    JLabel lifeTimeWr = new JLabel("Warriors lifetime:");
    JButton showObjs = new JButton("Current objects");
    JButton workerAIButton = new JButton("Workers AI on");
    JButton warriorsAIButton = new JButton("Warriors AI on");
    String[] priors = {"min", "normal", "max"};
    JLabel priority1 = new JLabel("Workers priority:");
    JLabel priority2 = new JLabel("Warriors priority:");
    JComboBox workersprior = new JComboBox(priors);
    JComboBox warriorsprior = new JComboBox(priors);





    public GUIFrame(String s) {
        super(s);

        nWorkers.setText("3");
        nWarriors.setText("4");
        workersLifeTime.setText("1");
        warriorsLifeTime.setText("1");

        nWorkers.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                super.keyTyped(keyEvent);
                char a = keyEvent.getKeyChar();
                if(!Character.isDigit(a)){
                    keyEvent.consume();
                }
            }
        });
        nWarriors.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                super.keyTyped(keyEvent);
                char b = keyEvent.getKeyChar();
                if(!Character.isDigit(b)){
                    keyEvent.consume();
                }
            }
        });

        workersLifeTime.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                super.keyTyped(keyEvent);
                char a = keyEvent.getKeyChar();
                if(!Character.isDigit(a)){
                    keyEvent.consume();
                }
            }
        });
        warriorsLifeTime.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                super.keyTyped(keyEvent);
                char b = keyEvent.getKeyChar();
                if(!Character.isDigit(b)){
                    keyEvent.consume();
                }
            }
        });

        jbox.setAlignmentX(LEFT_ALIGNMENT);
        failMenu.add(startItem);
        failMenu.add(stopItem);
        failMenu.add(timerItem);
        failMenu.add(consoleItem);
        failMenu.addSeparator();
        failMenu.add(saveItem);
        failMenu.add(loadItem);
        failMenu.addSeparator();
        failMenu.add(exitItem);

        jbar.add(failMenu);
        setJMenuBar(jbar);
        startItem.setEnabled(true);
        stopItem.setEnabled(false);

        start.setEnabled(true);
        stop.setEnabled(false);

        showTimeGr.add(showTimeOn);
        showTimeGr.add(showTimeOff);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);


        st.setPreferredSize(new Dimension(110, 20));
        av.setBorder(new LineBorder(Color.BLACK));
        p1.setLayout(new BorderLayout());
        p1.add(st, BorderLayout.NORTH);
        p1.add(av, BorderLayout.CENTER);

        p2.setLayout(new GridLayout(0,1));
        p2.add(p21);
        p2.add(p22);
        p2.add(p231);
        p2.add(p23);
        p2.add(p232);
        p2.add(p24);
        p2.add(p251);
        p2.add(p25);
        p2.add(p26);
        p2.add(p27);
        p2.add(p28);
        p2.add(p29);
        p21.add(start);
        p21.add(stop);
        p22.add(showTimeOn);
        p22.add(showTimeOff);
        p23.add(nWorkers);
        p23.add(nWarriors);
        p24.add(jbox);
        p231.add(q1);
        p231.add(q2);
        p232.add(chance1);
        p232.add(chance2);
        p24.add(jbox2);
        p251.add(lifeTimeWk);
        p251.add(lifeTimeWr);
        p25.add(workersLifeTime);
        p25.add(warriorsLifeTime);
        p26.add(showObjs);
        p27.add(workerAIButton);
        p27.add(warriorsAIButton);
        p28.add(priority1);
        p28.add(priority2);
        p29.add(workersprior);
        p29.add(warriorsprior);


        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(p1, BorderLayout.CENTER);
        mainPanel.add(p2, BorderLayout.EAST);
    }


}

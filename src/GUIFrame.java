import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;

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
    Button start = new Button("Start");
    Button stop = new Button("Stop");
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
    JMenuItem timerItem = new JMenuItem("Show/Hide item");
    JMenuItem exitItem = new JMenuItem("Exit");
    JTextField nWorkers = new JTextField(10);
    JTextField nWarriors = new JTextField(10);
    String[] pers = { "0%","10%","20%","30%","40%","50%","60%","70%","80%","90%","100%"};
    JComboBox jbox = new JComboBox(pers);
    JComboBox jbox2 = new JComboBox(pers);
    JLabel q1 = new JLabel("Workers spawn frequency:");
    JLabel q2 = new JLabel("Warriors spawn frequency:");
    JLabel chance1 = new JLabel("Workers spawn chance:");
    JLabel chance2 = new JLabel("Warriors spawn chance:");





    public GUIFrame(String s) {
        super(s);

        nWorkers.setText("3");
        nWarriors.setText("4");

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
                    nWarriors.setText("");
                }
            }
        });

        jbox.setAlignmentX(LEFT_ALIGNMENT);
        failMenu.add(startItem);
        failMenu.add(stopItem);
        failMenu.add(timerItem);
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

        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(mainPanel);


        st.setPreferredSize(new Dimension(110, 20));
        av.setPreferredSize(new Dimension(900, 600));
        av.setBorder(new LineBorder(Color.BLACK));
        p1.setLayout(new BorderLayout());
        p1.add(st, BorderLayout.NORTH);
        p1.add(av, BorderLayout.CENTER);

        p2.setLayout(new GridLayout(0,1));
        p21.setLayout(new FlowLayout());
        p22.setLayout(new FlowLayout());
        p23.setLayout(new FlowLayout());
        p231.setLayout(new FlowLayout());
        p232.setLayout(new FlowLayout());
        p24.setLayout(new FlowLayout());
        p2.add(p21);
        p2.add(p22);
        p2.add(p231);
        p2.add(p23);
        p2.add(p232);
        p2.add(p24);
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

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(p1, BorderLayout.CENTER);
        mainPanel.add(p2, BorderLayout.EAST);
    }


}

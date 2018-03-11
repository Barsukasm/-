import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Timer;

public class GUIFrame extends JFrame {

    JPanel mainPanel = new JPanel();
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    Button start = new Button("Start");
    Button stop = new Button("Stop");
    AntsVision av = new AntsVision();
    ShowTime st = new ShowTime();
    KeyboardFocusManager manager;
    JRadioButton showTimeOn = new JRadioButton("Show simulation time");
    JRadioButton showTimeOff = new JRadioButton("Do not show simulation time", true);
    ButtonGroup showTimeGr = new ButtonGroup();





    public GUIFrame(String s) {
        super(s);

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

        p2.setLayout(new FlowLayout());
        p2.add(start);
        p2.add(stop);
        p2.add(showTimeOn);
        p2.add(showTimeOff);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(p1, BorderLayout.CENTER);
        mainPanel.add(p2, BorderLayout.EAST);
    }


}

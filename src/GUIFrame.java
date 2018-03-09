import javax.swing.*;
import java.awt.*;

public class GUIFrame extends JFrame {

    Panel mainPanel = new Panel();
    Panel p1 = new Panel();
    Panel p2 = new Panel();
    Button start = new Button("Start");
    Button stop = new Button("Stop");
    AntsVision av = new AntsVision();
    ShowTime st = new ShowTime();





    public GUIFrame(String s){
        super(s);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(mainPanel);

        st.setSize(115,20);
        av.setSize(this.getHeight()-100,this.getWidth()-100);

        p1.setLayout(new BorderLayout());
        p1.add(st,BorderLayout.NORTH);
        p1.add(av,BorderLayout.CENTER);

        p2.setLayout(new BorderLayout());
        p2.add(start, BorderLayout.NORTH);
        p2.add(stop, BorderLayout.SOUTH);

        mainPanel.add(p1);
        mainPanel.add(p2);


    }
}

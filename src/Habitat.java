import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Habitat {

    private ArrayList<Ant> ants;

    private int n1,n2;
    private double p1,p2;

    public Habitat(int nw1, int nw2, double pw1, double pw2) {
        ants = new ArrayList<Ant>();
        n1 =nw1;
        n2 = nw2;
        p1 = pw1;
        p2 = pw2;
    }

    void update(){
        JFrame f = new JFrame("AntsSimulator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(25, 25, 300, 300);

        ants.add(new AntWorker(123, 225));
        ants.add(new AntWarrior(45, 10));

        AntsVision av = new AntsVision(ants);
        f.add(av);
        f.setVisible(true);

    }

}


